package com.bg.library.UI.View.DragRecyclerView;


import com.bg.library.UI.View.DragRecyclerView.indicator.DragIndicator;

/**
 * A single linked list to wrap DragUIHandler
 */
class DragUIHandlerHolder implements DragUIHandler {

    private DragUIHandler mHandler;
    private DragUIHandlerHolder mNext;

    private boolean contains(DragUIHandler handler) {
        return mHandler != null && mHandler == handler;
    }

    private DragUIHandlerHolder() {

    }

    public boolean hasHandler() {
        return mHandler != null;
    }

    private DragUIHandler getHandler() {
        return mHandler;
    }

    public static void addHandler(DragUIHandlerHolder head, DragUIHandler handler) {

        if (null == handler) {
            return;
        }
        if (head == null) {
            return;
        }
        if (null == head.mHandler) {
            head.mHandler = handler;
            return;
        }

        DragUIHandlerHolder current = head;
        for (; ; current = current.mNext) {

            // duplicated
            if (current.contains(handler)) {
                return;
            }
            if (current.mNext == null) {
                break;
            }
        }

        DragUIHandlerHolder newHolder = new DragUIHandlerHolder();
        newHolder.mHandler = handler;
        current.mNext = newHolder;
    }

    public static DragUIHandlerHolder create() {
        return new DragUIHandlerHolder();
    }

    public static DragUIHandlerHolder removeHandler(DragUIHandlerHolder head, DragUIHandler handler) {
        if (head == null || handler == null || null == head.mHandler) {
            return head;
        }

        DragUIHandlerHolder current = head;
        DragUIHandlerHolder pre = null;
        do {

            // delete current: link pre to next, unlink next from current;
            // pre will no change, current move to next element;
            if (current.contains(handler)) {

                // current is head
                if (pre == null) {

                    head = current.mNext;
                    current.mNext = null;

                    current = head;
                } else {

                    pre.mNext = current.mNext;
                    current.mNext = null;
                    current = pre.mNext;
                }
            } else {
                pre = current;
                current = current.mNext;
            }

        } while (current != null);

        if (head == null) {
            head = new DragUIHandlerHolder();
        }
        return head;
    }

    @Override
    public void onUIReset(DragFrameLayout frame) {
        DragUIHandlerHolder current = this;
        do {
            final DragUIHandler handler = current.getHandler();
            if (null != handler) {
                handler.onUIReset(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshPrepare(DragFrameLayout frame) {
        if (!hasHandler()) {
            return;
        }
        DragUIHandlerHolder current = this;
        do {
            final DragUIHandler handler = current.getHandler();
            if (null != handler) {
                handler.onUIRefreshPrepare(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshBegin(DragFrameLayout frame) {
        DragUIHandlerHolder current = this;
        do {
            final DragUIHandler handler = current.getHandler();
            if (null != handler) {
                handler.onUIRefreshBegin(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIRefreshComplete(DragFrameLayout frame) {
        DragUIHandlerHolder current = this;
        do {
            final DragUIHandler handler = current.getHandler();
            if (null != handler) {
                handler.onUIRefreshComplete(frame);
            }
        } while ((current = current.mNext) != null);
    }

    @Override
    public void onUIPositionChange(DragFrameLayout frame, boolean isUnderTouch, byte status, DragIndicator dragIndicator) {
        DragUIHandlerHolder current = this;
        do {
            final DragUIHandler handler = current.getHandler();
            if (null != handler) {
                handler.onUIPositionChange(frame, isUnderTouch, status, dragIndicator);
            }
        } while ((current = current.mNext) != null);
    }
}
