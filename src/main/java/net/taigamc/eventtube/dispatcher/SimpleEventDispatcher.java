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
package net.taigamc.eventtube.dispatcher;

import java.util.function.BiConsumer;
import net.taigamc.eventtube.event.EventRegistry;
import net.taigamc.eventtube.handler.EventHandler;
import net.taigamc.eventtube.handler.EventHandlerException;
import net.taigamc.eventtube.handler.EventHandlerRegistry;

/**
 * Simple implementation of event dispatcher.
 *
 * @since 1.0.0
 */
public interface SimpleEventDispatcher extends EventDispatcher, EventHandlerRegistry, EventRegistry {

  /**
   * Creates a new simple event dispatcher implementation.
   *
   * @param errorHandler error handler
   * @return created implementation
   *
   * @since 1.0.0
   */
  static SimpleEventDispatcher create(final BiConsumer<EventHandler<?>, EventHandlerException> errorHandler) {
    return new SimpleEventDispatcherImpl(errorHandler);
  }

}
