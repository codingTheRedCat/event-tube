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

import java.util.function.Consumer;
import net.taigamc.eventtube.event.Event;
import net.taigamc.eventtube.event.EventPriority;

class FunctionalEventHandlerImpl<E extends Event> extends AbstractEventHandler<E> {

  private final Consumer<E> consumer;

  protected FunctionalEventHandlerImpl(final EventPriority priority, final boolean ignoresCancelled, final Class<E> eventClass, final Consumer<E> consumer) {
    super(priority, ignoresCancelled, eventClass);
    this.consumer = consumer;
  }

  @Override
  public void handle(final E event) throws EventHandlerException {
    if (this.shouldHandle(event)) {
      try {
        this.consumer.accept(event);
      } catch (final Throwable e) {
        throw new EventHandlerException(e);
      }
    }
  }

}
