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

import net.taigamc.eventtube.annotations.HandleCancelled;
import net.taigamc.eventtube.annotations.Priority;
import net.taigamc.eventtube.event.AbstractEvent;
import net.taigamc.eventtube.event.CancellableEvent;
import net.taigamc.eventtube.event.Event;
import net.taigamc.eventtube.event.EventPriority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ReflectionEventHandlerImplTest {

  @net.taigamc.eventtube.annotations.EventHandler
  void alwaysException(final Event event) {
    throw new IllegalStateException("Exception");
  }

  private Event event = null;

  @net.taigamc.eventtube.annotations.EventHandler
  void executionTest(final Event event) {
    this.event = event;
  }

  @net.taigamc.eventtube.annotations.EventHandler
  @HandleCancelled
  @Priority(EventPriority.LATE)
  void propertiesTest(final CancellableEvent event) {

  }

  @Test
  @SuppressWarnings("unchecked")
  void handle() throws NoSuchMethodException {
    final EventHandler<?> propertiesTest = EventHandler.reflection(this.getClass().getDeclaredMethod("propertiesTest", CancellableEvent.class), this);

    assertFalse(propertiesTest.ignoresCancelled());
    assertEquals(EventPriority.LATE, propertiesTest.priority());
    assertEquals(CancellableEvent.class, propertiesTest.eventClass());

    final EventHandler<Event> exceptionTest = (EventHandler<Event>) EventHandler.reflection(this.getClass().getDeclaredMethod("alwaysException", Event.class), this);

    assertThrows(EventHandlerException.class, () -> exceptionTest.handle(new AbstractEvent() {
    }));

    try {
      exceptionTest.handle(new AbstractEvent() {
      });
    } catch (final EventHandlerException e) {
      assertEquals(IllegalStateException.class, e.getCause().getClass());
    }

    final EventHandler<Event> executionTest = (EventHandler<Event>) EventHandler.reflection(this.getClass().getDeclaredMethod("executionTest", Event.class), this);

    final Event event = new AbstractEvent() {
    };

    assertDoesNotThrow(() -> executionTest.handle(event));

    assertEquals(event, this.event);

  }
}
