/***** Lobxxx Translate Finished ******/
/*
 * Copyright (c) 1996, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */

package java.awt;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;

import java.util.ArrayList;
import sun.util.logging.PlatformLogger;

import sun.awt.dnd.SunDragSourceContextPeer;
import sun.awt.EventQueueDelegate;

/**
 * EventDispatchThread is a package-private AWT class which takes
 * events off the EventQueue and dispatches them to the appropriate
 * AWT components.
 *
 * The Thread starts a "permanent" event pump with a call to
 * pumpEvents(Conditional) in its run() method. Event handlers can choose to
 * block this event pump at any time, but should start a new pump (<b>not</b>
 * a new EventDispatchThread) by again calling pumpEvents(Conditional). This
 * secondary event pump will exit automatically as soon as the Condtional
 * evaluate()s to false and an additional Event is pumped and dispatched.
 *
 * <p>
 *  EventDispatchThread是一个包私有AWT类,它将事件从EventQueue中取出,并将它们分派到相应的AWT组件。
 * 
 *  线程启动一个"永久"事件泵,并在其run()方法中调用pumpEvents(Conditional)。
 * 事件处理程序可以选择在任何时候阻塞此事件泵,但应通过再次调用pumpEvents(有条件)启动一个新泵(<b>不是</b>一个新的EventDispatchThread)。
 * 只要Condtional evaluate()为false并且附加事件被泵送和分派,该辅助事件泵将自动退出。
 * 
 * 
 * @author Tom Ball
 * @author Amy Fowler
 * @author Fred Ecks
 * @author David Mendenhall
 *
 * @since 1.1
 */
class EventDispatchThread extends Thread {

    private static final PlatformLogger eventLog = PlatformLogger.getLogger("java.awt.event.EventDispatchThread");

    private EventQueue theQueue;
    private volatile boolean doDispatch = true;

    private static final int ANY_EVENT = -1;

    private ArrayList<EventFilter> eventFilters = new ArrayList<EventFilter>();

    EventDispatchThread(ThreadGroup group, String name, EventQueue queue) {
        super(group, name);
        setEventQueue(queue);
    }

    /*
     * Must be called on EDT only, that's why no synchronization
     * <p>
     *  必须在EDT上调用,这就是为什么没有同步
     * 
     */
    public void stopDispatching() {
        doDispatch = false;
    }

    public void run() {
        try {
            pumpEvents(new Conditional() {
                public boolean evaluate() {
                    return true;
                }
            });
        } finally {
            getEventQueue().detachDispatchThread(this);
        }
    }

    void pumpEvents(Conditional cond) {
        pumpEvents(ANY_EVENT, cond);
    }

    void pumpEventsForHierarchy(Conditional cond, Component modalComponent) {
        pumpEventsForHierarchy(ANY_EVENT, cond, modalComponent);
    }

    void pumpEvents(int id, Conditional cond) {
        pumpEventsForHierarchy(id, cond, null);
    }

    void pumpEventsForHierarchy(int id, Conditional cond, Component modalComponent) {
        pumpEventsForFilter(id, cond, new HierarchyEventFilter(modalComponent));
    }

    void pumpEventsForFilter(Conditional cond, EventFilter filter) {
        pumpEventsForFilter(ANY_EVENT, cond, filter);
    }

    void pumpEventsForFilter(int id, Conditional cond, EventFilter filter) {
        addEventFilter(filter);
        doDispatch = true;
        while (doDispatch && !isInterrupted() && cond.evaluate()) {
            pumpOneEventForFilters(id);
        }
        removeEventFilter(filter);
    }

    void addEventFilter(EventFilter filter) {
        if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
            eventLog.finest("adding the event filter: " + filter);
        }
        synchronized (eventFilters) {
            if (!eventFilters.contains(filter)) {
                if (filter instanceof ModalEventFilter) {
                    ModalEventFilter newFilter = (ModalEventFilter)filter;
                    int k = 0;
                    for (k = 0; k < eventFilters.size(); k++) {
                        EventFilter f = eventFilters.get(k);
                        if (f instanceof ModalEventFilter) {
                            ModalEventFilter cf = (ModalEventFilter)f;
                            if (cf.compareTo(newFilter) > 0) {
                                break;
                            }
                        }
                    }
                    eventFilters.add(k, filter);
                } else {
                    eventFilters.add(filter);
                }
            }
        }
    }

    void removeEventFilter(EventFilter filter) {
        if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
            eventLog.finest("removing the event filter: " + filter);
        }
        synchronized (eventFilters) {
            eventFilters.remove(filter);
        }
    }

    void pumpOneEventForFilters(int id) {
        AWTEvent event = null;
        boolean eventOK = false;
        try {
            EventQueue eq = null;
            EventQueueDelegate.Delegate delegate = null;
            do {
                // EventQueue may change during the dispatching
                eq = getEventQueue();
                delegate = EventQueueDelegate.getDelegate();

                if (delegate != null && id == ANY_EVENT) {
                    event = delegate.getNextEvent(eq);
                } else {
                    event = (id == ANY_EVENT) ? eq.getNextEvent() : eq.getNextEvent(id);
                }

                eventOK = true;
                synchronized (eventFilters) {
                    for (int i = eventFilters.size() - 1; i >= 0; i--) {
                        EventFilter f = eventFilters.get(i);
                        EventFilter.FilterAction accept = f.acceptEvent(event);
                        if (accept == EventFilter.FilterAction.REJECT) {
                            eventOK = false;
                            break;
                        } else if (accept == EventFilter.FilterAction.ACCEPT_IMMEDIATELY) {
                            break;
                        }
                    }
                }
                eventOK = eventOK && SunDragSourceContextPeer.checkEvent(event);
                if (!eventOK) {
                    event.consume();
                }
            }
            while (eventOK == false);

            if (eventLog.isLoggable(PlatformLogger.Level.FINEST)) {
                eventLog.finest("Dispatching: " + event);
            }

            Object handle = null;
            if (delegate != null) {
                handle = delegate.beforeDispatch(event);
            }
            eq.dispatchEvent(event);
            if (delegate != null) {
                delegate.afterDispatch(event, handle);
            }
        }
        catch (ThreadDeath death) {
            doDispatch = false;
            throw death;
        }
        catch (InterruptedException interruptedException) {
            doDispatch = false; // AppContext.dispose() interrupts all
                                // Threads in the AppContext
        }
        catch (Throwable e) {
            processException(e);
        }
    }

    private void processException(Throwable e) {
        if (eventLog.isLoggable(PlatformLogger.Level.FINE)) {
            eventLog.fine("Processing exception: " + e);
        }
        getUncaughtExceptionHandler().uncaughtException(this, e);
    }

    public synchronized EventQueue getEventQueue() {
        return theQueue;
    }
    public synchronized void setEventQueue(EventQueue eq) {
        theQueue = eq;
    }

    private static class HierarchyEventFilter implements EventFilter {
        private Component modalComponent;
        public HierarchyEventFilter(Component modalComponent) {
            this.modalComponent = modalComponent;
        }
        public FilterAction acceptEvent(AWTEvent event) {
            if (modalComponent != null) {
                int eventID = event.getID();
                boolean mouseEvent = (eventID >= MouseEvent.MOUSE_FIRST) &&
                                     (eventID <= MouseEvent.MOUSE_LAST);
                boolean actionEvent = (eventID >= ActionEvent.ACTION_FIRST) &&
                                      (eventID <= ActionEvent.ACTION_LAST);
                boolean windowClosingEvent = (eventID == WindowEvent.WINDOW_CLOSING);
                /*
                 * filter out MouseEvent and ActionEvent that's outside
                 * the modalComponent hierarchy.
                 * KeyEvent is handled by using enqueueKeyEvent
                 * in Dialog.show
                 * <p>
                 *  过滤掉在modalComponent层次结构之外的MouseEvent和ActionEvent。 KeyEvent通过在Dialog.show中使用enqueueKeyEvent来处理
                 * 
                 */
                if (Component.isInstanceOf(modalComponent, "javax.swing.JInternalFrame")) {
                    /*
                     * Modal internal frames are handled separately. If event is
                     * for some component from another heavyweight than modalComp,
                     * it is accepted. If heavyweight is the same - we still accept
                     * event and perform further filtering in LightweightDispatcher
                     * <p>
                     *  模态内部框架单独处理。如果事件是来自另一个重量超过modalComp的一些组件,它被接受。如果重量相同 - 我们仍然接受事件并在LightweightDispatcher中执行进一步过滤
                     */
                    return windowClosingEvent ? FilterAction.REJECT : FilterAction.ACCEPT;
                }
                if (mouseEvent || actionEvent || windowClosingEvent) {
                    Object o = event.getSource();
                    if (o instanceof sun.awt.ModalExclude) {
                        // Exclude this object from modality and
                        // continue to pump it's events.
                        return FilterAction.ACCEPT;
                    } else if (o instanceof Component) {
                        Component c = (Component) o;
                        // 5.0u3 modal exclusion
                        boolean modalExcluded = false;
                        if (modalComponent instanceof Container) {
                            while (c != modalComponent && c != null) {
                                if ((c instanceof Window) &&
                                    (sun.awt.SunToolkit.isModalExcluded((Window)c))) {
                                    // Exclude this window and all its children from
                                    //  modality and continue to pump it's events.
                                    modalExcluded = true;
                                    break;
                                }
                                c = c.getParent();
                            }
                        }
                        if (!modalExcluded && (c != modalComponent)) {
                            return FilterAction.REJECT;
                        }
                    }
                }
            }
            return FilterAction.ACCEPT;
        }
    }
}