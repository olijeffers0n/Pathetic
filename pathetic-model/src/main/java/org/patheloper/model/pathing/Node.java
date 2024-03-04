package org.patheloper.model.pathing;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.patheloper.api.pathing.configuration.HeuristicWeights;
import org.patheloper.api.pathing.configuration.PathingRuleSet;
import org.patheloper.api.wrapper.PathPosition;
import org.patheloper.api.wrapper.PathVector;
import org.patheloper.util.ComputingCache;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@RequiredArgsConstructor
public class Node implements Comparable<Node> {

  private static final int NUMBER_OF_ZONES = 4;
  private static final double MAX_PENALTY = 2.0;

  @EqualsAndHashCode.Include private final PathPosition position;
  private final PathPosition start;
  private final PathPosition target;
  private final PathingRuleSet ruleSet; // we need to find a better solution for this
  private final Integer depth;

  private final ComputingCache<Double> fCostCache = new ComputingCache<>(this::calculateFCost);
  private final ComputingCache<Double> gCostCache = new ComputingCache<>(this::calculateGCost);
  private final ComputingCache<Double> heuristic = new ComputingCache<>(this::heuristic);

  private double[] zonePenalties;

  private Node parent;

  public void setParent(Node parent) {
    this.parent = parent;
  }

  public boolean isTarget() {
    return this.position.getBlockX() == target.getBlockX()
        && this.position.getBlockY() == target.getBlockY()
        && this.position.getBlockZ() == target.getBlockZ();
  }

  /**
   * Calculates the estimated total cost of the path from the start node to the goal node, passing
   * through this node.
   *
   * @return the estimated total cost (represented by the F-Score)
   */
  private double getFCost() {
    return fCostCache.get();
  }

  /**
   * The accumulated cost (also known as G-Score) from the starting node to the current node. This
   * value represents the actual (known) cost of traversing the path to the current node. It is
   * typically calculated by summing the movement costs from the start node to the current node.
   */
  private double getGCost() {
    return gCostCache.get();
  }

  private double calculateFCost() {
    return getGCost() + heuristic.get();
  }

  private double calculateGCost() {
    if (parent == null) {
      return 0;
    }
    return parent.getGCost() + position.distance(parent.position);
  }

  private double heuristic() {
    double manhattanDistance = this.position.manhattanDistance(target);
    double octileDistance = this.position.octileDistance(target);
    double perpendicularDistance;
    if(ruleSet.isApproximatePerpendicularDistance()) {
      calculateZones();
      perpendicularDistance = approximatePerpendicularDistance();
    } else {
      perpendicularDistance = calculatePerpendicularDistance();
    }
    double heightFactor =
        Math.abs(this.position.getBlockY() - target.getBlockY()); // Consider height differences

    HeuristicWeights heuristicWeights = ruleSet.getHeuristicWeights();
    return (manhattanDistance * heuristicWeights.getManhattenWeight())
        + (octileDistance * heuristicWeights.getOctileWeight())
        + (perpendicularDistance * heuristicWeights.getPerpendicularWeight())
        + (heightFactor * heuristicWeights.getHeightWeight());
  }

  private void calculateZones() {
    zonePenalties = new double[NUMBER_OF_ZONES];
    double penaltyIncrement = MAX_PENALTY / NUMBER_OF_ZONES;

    for (int i = 0; i < NUMBER_OF_ZONES; i++) {
      zonePenalties[i] = penaltyIncrement * (i + 1);
    }
  }

  private double approximatePerpendicularDistance() {
    PathVector pathToStart = start.toVector().subtract(position.toVector());
    PathVector pathToTarget = target.toVector().subtract(position.toVector());

    int zone = (int) Math.abs(pathToStart.getCrossProduct(pathToTarget).length() / 4.0);
    zone = Math.min(zone, zonePenalties.length - 1);

    return zonePenalties[zone];
  }

  private double calculatePerpendicularDistance() {
    PathVector pathToStart = start.toVector().subtract(position.toVector());
    PathVector pathToTarget = target.toVector().subtract(position.toVector());
    return pathToStart.getCrossProduct(pathToTarget).length() / pathToTarget.length();
  }

  @Override
  public int compareTo(Node o) {
    // This is used in the priority queue to sort the nodes
    return Double.compare(getFCost(), o.getFCost());
  }
}
