/*
 * This file is part of event tube, licensed under GNU General Public License v3.0
 * Copyright (C)2022 TaigaMC
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * at your option any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package net.taigamc.eventtube.event;

import org.jetbrains.annotations.ApiStatus;

/**
 * Stores information about write-lock state of an event. Can be one-way locked.
 *
 * @since 1.0.0
 */
@ApiStatus.Internal
public final class EventLock {

  /**
   * Creates a new event lock that have not been locked yet.
   *
   * @return new lock
   *
   * @since 1.0.0
   */
  public static EventLock newLock() {
    return new EventLock();
  }

  private boolean lockState;

  private EventLock() {
    this.lockState = false;
  }

  /**
   * Gets the write-lock state.
   *
   * @return true when it is locked or false when it is not.
   *
   * @since 1.0.0
   */
  public boolean lockState() {
    return this.lockState;
  }

  /**
   * Changes write-lock state to true. Can not be undone.
   *
   * @since 1.0.0
   */
  public void lock() {
    this.lockState = true;
  }

}
