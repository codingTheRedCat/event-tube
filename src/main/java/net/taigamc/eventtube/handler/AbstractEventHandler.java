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
package net.taigamc.eventtube.handler;

import net.taigamc.eventtube.event.CancellableEvent;
import net.taigamc.eventtube.event.Event;
import net.taigamc.eventtube.event.EventPriority;

/**
 * Provides basic properties of an event handler.
 *
 * @param <E> event type
 *
 * @since 1.0.0
 */
public abstract class AbstractEventHandler<E extends Event> implements EventHandler<E> {

  private final EventPriority priority;

  private final boolean ignoresCancelled;

  private final Class<?> eventClass;

  protected AbstractEventHandler(final EventPriority priority, final boolean ignoresCancelled, final Class<?> eventClass) {
    this.priority = priority;
    this.ignoresCancelled = ignoresCancelled;
    this.eventClass = eventClass;
  }

  @Override
  public EventPriority priority() {
    return this.priority;
  }

  @Override
  public boolean ignoresCancelled() {
    return this.ignoresCancelled;
  }

  @Override
  public Class<?> eventClass() {
    return this.eventClass;
  }

  protected boolean shouldHandle(final E event) {
    return !this.ignoresCancelled || !CancellableEvent.cancelled(event);
  }

}
