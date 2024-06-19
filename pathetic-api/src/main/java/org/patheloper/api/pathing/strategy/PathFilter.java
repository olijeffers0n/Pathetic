package org.patheloper.api.pathing.strategy;

import lombok.NonNull;
import org.patheloper.api.pathing.Pathfinder;
import org.patheloper.api.snapshot.SnapshotManager;
import org.patheloper.api.wrapper.PathPosition;

/**
 * A PathFilter is a functional interface that allows customization of the pathfinding process
 * within the {@link Pathfinder}. It provides a mechanism to influence the selection of paths during
 * the pathfinding algorithm's execution.
 */
@FunctionalInterface
public interface PathFilter {

  /**
   * Evaluates the given {@link PathValidationContext} to determine if the path is valid. This
   * method is used during the pathfinding process to filter out unwanted paths.
   *
   * @param pathValidationContext The context providing the information necessary to evaluate
   * @return true if the path is valid, false otherwise
   */
  boolean filter(@NonNull PathValidationContext pathValidationContext);

  /**
   * Cleans up the resources used during the pathfinding process. This method is guaranteed to
   * always be called after pathfinding and should be overridden to ensure proper disposal of
   * resources. Users can rely on the fact that this method will be invoked post pathfinding to do
   * necessary clean-ups.
   */
  default void cleanup() {}
}