package jp.pinkikki.account.book.ui

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import jp.pinkikki.account.book.R
import jp.pinkikki.account.book.databinding.ItemRowBinding

class ItemListAdapter(context: Context, itemRows: List<ItemRow>) :
    ArrayAdapter<ItemRow>(context, 0, itemRows) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        val binding: ItemRowBinding

        if (view == null) {
            binding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_row,
                        parent,
                        false
                    )
            view = binding.root
            view.tag = binding

            if (position % 2 == 1) {
                view.setBackgroundColor(Color.argb(67, 205, 205, 205))
            }
        } else {
            binding = view.tag as ItemRowBinding
        }
        binding.model = getItem(position)
        return view
    }
}