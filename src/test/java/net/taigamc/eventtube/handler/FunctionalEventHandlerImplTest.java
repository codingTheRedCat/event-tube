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

import java.util.concurrent.atomic.AtomicBoolean;
import net.taigamc.eventtube.event.AbstractCancellableEvent;
import net.taigamc.eventtube.event.AbstractEvent;
import net.taigamc.eventtube.event.CancellableEvent;
import net.taigamc.eventtube.event.Event;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionalEventHandlerImplTest {

  @Test
  void handle() {
    final AtomicBoolean executed = new AtomicBoolean(false);
    {
      final EventHandler<CancellableEvent> handler = EventHandler.functional(CancellableEvent.class, event -> executed.set(true));
      assertDoesNotThrow(() -> handler.handle(new AbstractCancellableEvent("event") {
      }));
      assertTrue(executed.get());
      executed.set(false);
      final CancellableEvent event = new AbstractCancellableEvent("event") {
      };
      event.cancelled(true);
      assertDoesNotThrow(() -> handler.handle(event));
      assertFalse(executed.get());
      executed.set(false);
    }
    {
      final EventHandler<CancellableEvent> handler = EventHandler.functional(CancellableEvent.class, event -> executed.set(true), false);
      assertDoesNotThrow(() -> handler.handle(new AbstractCancellableEvent("event") {
      }));
      assertTrue(executed.get());
      executed.set(false);
      final CancellableEvent event = new AbstractCancellableEvent("event") {
      };
      event.cancelled(true);
      assertDoesNotThrow(() -> handler.handle(event));
      assertTrue(executed.get());
      executed.set(false);
    }
    {
      final EventHandler<Event> handler = EventHandler.functional(Event.class, event -> {
        throw new UnsupportedOperationException("exception");
      });
      assertThrows(EventHandlerException.class, () -> handler.handle(new AbstractEvent("an event") {
      }));
    }
  }

}
