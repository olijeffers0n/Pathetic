package de.metaphoriker.pathetic.api.pathing.filter;

import de.metaphoriker.pathetic.api.provider.NavigationPointProvider;
import de.metaphoriker.pathetic.api.wrapper.PathPosition;

/**
 * PathValidationContext is a data container used during the pathfinding process to provide relevant
 * contextual information needed for evaluating path validity.
 *
 * <p>This context is passed to {@link PathFilter#filter} methods during the pathfinding process and
 * allows filters to validate or invalidate nodes based on the provided context.
 */
public final class PathValidationContext {

  /**
   * The current position being evaluated in the pathfinding process. This represents the position
   * that is being validated by the filter to determine if it can be part of a valid path.
   */
  private final PathPosition position;

  /**
   * The parent position of the current position. This is the previous node from which the current
   * position was reached. It is used to trace the path and ensure logical continuity between nodes.
   */
  private final PathPosition parent;

  /**
   * The absolute start position of the pathfinding process. This represents the original starting
   * point of the path and remains constant throughout the algorithm, providing a stable reference.
   */
  private final PathPosition absoluteStart;

  /**
   * The absolute target position of the pathfinding process. This is the final goal or destination
   * that the pathfinding algorithm is trying to reach. Like the start, it remains constant and
   * provides a clear end-point for the path.
   */
  private final PathPosition absoluteTarget;

  /**
   * The BlockProvider provides access to world data, such as block information, in the context of
   * the pathfinding process. It is used to retrieve block data from the world at different
   * positions during path validation.
   */
  private final NavigationPointProvider navigationPointProvider;

  public PathValidationContext(
      PathPosition position,
      PathPosition parent,
      PathPosition absoluteStart,
      PathPosition absoluteTarget,
      NavigationPointProvider navigationPointProvider) {
    this.position = position;
    this.parent = parent;
    this.absoluteStart = absoluteStart;
    this.absoluteTarget = absoluteTarget;
    this.navigationPointProvider = navigationPointProvider;
  }

  public PathPosition getPosition() {
    return this.position;
  }

  public PathPosition getParent() {
    return this.parent;
  }

  public PathPosition getAbsoluteStart() {
    return this.absoluteStart;
  }

  public PathPosition getAbsoluteTarget() {
    return this.absoluteTarget;
  }

  public NavigationPointProvider getNavigationPointProvider() {
    return this.navigationPointProvider;
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof PathValidationContext)) return false;
    final PathValidationContext other = (PathValidationContext) o;
    final Object this$position = this.getPosition();
    final Object other$position = other.getPosition();
    if (this$position == null ? other$position != null : !this$position.equals(other$position))
      return false;
    final Object this$parent = this.getParent();
    final Object other$parent = other.getParent();
    if (this$parent == null ? other$parent != null : !this$parent.equals(other$parent))
      return false;
    final Object this$absoluteStart = this.getAbsoluteStart();
    final Object other$absoluteStart = other.getAbsoluteStart();
    if (this$absoluteStart == null
        ? other$absoluteStart != null
        : !this$absoluteStart.equals(other$absoluteStart)) return false;
    final Object this$absoluteTarget = this.getAbsoluteTarget();
    final Object other$absoluteTarget = other.getAbsoluteTarget();
    if (this$absoluteTarget == null
        ? other$absoluteTarget != null
        : !this$absoluteTarget.equals(other$absoluteTarget)) return false;
    final Object this$blockProvider = this.getNavigationPointProvider();
    final Object other$blockProvider = other.getNavigationPointProvider();
    if (this$blockProvider == null
        ? other$blockProvider != null
        : !this$blockProvider.equals(other$blockProvider)) return false;
    return true;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $position = this.getPosition();
    result = result * PRIME + ($position == null ? 43 : $position.hashCode());
    final Object $parent = this.getParent();
    result = result * PRIME + ($parent == null ? 43 : $parent.hashCode());
    final Object $absoluteStart = this.getAbsoluteStart();
    result = result * PRIME + ($absoluteStart == null ? 43 : $absoluteStart.hashCode());
    final Object $absoluteTarget = this.getAbsoluteTarget();
    result = result * PRIME + ($absoluteTarget == null ? 43 : $absoluteTarget.hashCode());
    final Object $blockProvider = this.getNavigationPointProvider();
    result = result * PRIME + ($blockProvider == null ? 43 : $blockProvider.hashCode());
    return result;
  }

  public String toString() {
    return "PathValidationContext(position="
        + this.getPosition()
        + ", parent="
        + this.getParent()
        + ", absoluteStart="
        + this.getAbsoluteStart()
        + ", absoluteTarget="
        + this.getAbsoluteTarget()
        + ", navigationPointProvider="
        + this.getNavigationPointProvider()
        + ")";
  }
}
