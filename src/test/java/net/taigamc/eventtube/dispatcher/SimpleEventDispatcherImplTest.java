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

import java.util.LinkedList;
import java.util.List;
import net.taigamc.eventtube.annotations.HandleCancelled;
import net.taigamc.eventtube.annotations.Priority;
import net.taigamc.eventtube.event.AbstractCancellableEvent;
import net.taigamc.eventtube.event.AbstractEvent;
import net.taigamc.eventtube.event.CancellableEvent;
import net.taigamc.eventtube.event.EventPriority;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SimpleEventDispatcherImplTest {

  @Test
  void classRegister() {
    final SimpleEventDispatcher dispatcher = SimpleEventDispatcher.create((h, e) -> e.printStackTrace());

    assertThrows(IllegalStateException.class, () -> dispatcher.dispatch(new AbstractEvent() {
    }, AbstractEvent.class));

    dispatcher.registerEvent(AbstractEvent.class);

    assertDoesNotThrow(() -> dispatcher.dispatch(new AbstractEvent() {
    }, AbstractEvent.class));

  }

  List<String> executed = new LinkedList<>();

  boolean enableCanceller = false;

  @net.taigamc.eventtube.annotations.EventHandler
  @Priority(EventPriority.EARLIER)
  void first(final CancellableEvent event) {
    this.executed.add("first");
  }

  @net.taigamc.eventtube.annotations.EventHandler
  @Priority(EventPriority.NORMAL)
  void second(final CancellableEvent event) {
    this.executed.add("second");
  }

  @net.taigamc.eventtube.annotations.EventHandler
  @Priority(EventPriority.LATER)
  @HandleCancelled
  void third(final CancellableEvent event) {
    this.executed.add("third");
  }

  @net.taigamc.eventtube.annotations.EventHandler
  @Priority(EventPriority.WATCHER)
  void fourth(final CancellableEvent event) {
    assertThrows(IllegalStateException.class, () -> event.cancelled(true));
    this.executed.add("fourth");
  }

  @net.taigamc.eventtube.annotations.EventHandler
  @Priority(EventPriority.EARLY)
  void canceller(final CancellableEvent event) {
    if (this.enableCanceller) {
      event.cancelled(true);
    }
  }

  @Test
  void handlerOrder() {

    final SimpleEventDispatcher dispatcher = SimpleEventDispatcher.create((h, e) -> e.printStackTrace());
    dispatcher.registerEvent(CancellableEvent.class);

    dispatcher.registerHandlers(this);
    dispatcher.dispatch(new AbstractCancellableEvent() {
    }, CancellableEvent.class);

    assertEquals(List.of("first", "second", "third", "fourth"), this.executed);

    this.executed.clear();
    this.enableCanceller = true;

    dispatcher.dispatch(new AbstractCancellableEvent() {
    }, CancellableEvent.class);

    assertEquals(List.of("first", "third"), this.executed);

  }
}
