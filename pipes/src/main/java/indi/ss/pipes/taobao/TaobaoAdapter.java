package indi.ss.pipes.taobao;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import com.ss.aris.open.image.WrapImageLoader;
import java.util.List;

public class TaobaoAdapter extends RecyclerView.Adapter<TaobaoAdapter.TaobaoViewHolder>{

    private Context context;
    private List<TaobaoItem> list;

    public TaobaoAdapter(Context context, List<TaobaoItem> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public TaobaoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new TaobaoViewHolder(new TaobaoViewGenerator(context));
    }

    @Override
    public void onBindViewHolder(TaobaoViewHolder holder, int position) {
        TaobaoItem item = list.get(position);
        holder.viewGenerator.title.setText(item.getTitle());
        holder.viewGenerator.origin.setText(item.getSellerLoc());
        holder.viewGenerator.price.setText(item.getPrice());
        WrapImageLoader.getInstance().displayImage(
                item.getImg2(), holder.viewGenerator.icon);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class TaobaoViewHolder extends RecyclerView.ViewHolder{

        TaobaoViewGenerator viewGenerator;

        public TaobaoViewHolder(TaobaoViewGenerator viewGenerator) {
            super(viewGenerator.get());
            this.viewGenerator = viewGenerator;
        }

    }
}