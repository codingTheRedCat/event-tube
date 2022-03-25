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
 * Provides mutation lock system for subclasses.
 *
 * @since 1.0.0
 */
public abstract class AbstractMutableEvent extends AbstractEvent implements MutableEvent {

  private final EventLock writeLock;

  protected AbstractMutableEvent(final String name) {
    super(name);
    this.writeLock = EventLock.newLock();
  }

  protected AbstractMutableEvent() {
    this.writeLock = EventLock.newLock();
  }

  /**
   * Gets mutation lock state.
   *
   * @return lock state
   *
   * @since 1.0.0
   */
  @ApiStatus.Internal
  public boolean writeLocked() {
    return this.writeLock.lockState();
  }

  protected void writeLockCheck() throws IllegalStateException {
    if (this.writeLocked()) throw new IllegalStateException("Write operations are not available for handler with WATCHER priority.");
  }

  /**
   * Locks the mutation lock.
   *
   * @since 1.0.0
   */
  @ApiStatus.Internal
  public void lock() {
    this.writeLock.lock();
  }

}
