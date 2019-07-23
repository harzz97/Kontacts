package io.github.harzz.kontacts

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class CustomRecyclerView : RecyclerView {

    private lateinit var emptyView : View

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {}


    private fun initEmptyView() {
        if (emptyView != null) {
            emptyView.visibility = if (adapter == null || adapter!!.itemCount == 0) View.VISIBLE else View.GONE
            this.visibility = if (adapter == null || adapter!!.itemCount == 0) View.GONE else View.VISIBLE
        }
    }

    val observer: RecyclerView.AdapterDataObserver = object : RecyclerView.AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            initEmptyView()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            initEmptyView()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            super.onItemRangeRemoved(positionStart, itemCount)
            initEmptyView()
        }
    }

    override fun setAdapter(newAdapter: RecyclerView.Adapter<*>?) {
        val oldAdapter = getAdapter()
        super.setAdapter(newAdapter)
        oldAdapter?.unregisterAdapterDataObserver(observer)
        newAdapter?.registerAdapterDataObserver(observer)
    }

    fun setEmptyView(view: View) {
        this.emptyView = view
        initEmptyView()
    }
}