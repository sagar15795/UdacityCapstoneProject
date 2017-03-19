package com.lusifer.shabdkosh.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.lusifer.shabdkosh.R;
import com.lusifer.shabdkosh.data.DataManager;
import com.lusifer.shabdkosh.data.local.DBHelper;
import com.lusifer.shabdkosh.data.model.local.ModelType;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel;
import com.lusifer.shabdkosh.data.model.local.RecentFavouriteModel_Table;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.ArrayList;
import java.util.List;

import butterknife.internal.Utils;

public class StockWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StockViewsFactory(this.getApplicationContext(),
                intent);
    }

    class StockViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        private Context ctxt;
        private int appWidgetId;
        private List<RecentFavouriteModel> recentFavouriteModels;

        public StockViewsFactory(Context ctxt, Intent intent) {
            this.ctxt = ctxt;
            appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                    AppWidgetManager.INVALID_APPWIDGET_ID);

        }

        @Override
        public void onCreate() {
            // no-op
        }

        @Override
        public void onDestroy() {
            // no-op
        }

        @Override
        public int getCount() {
            return recentFavouriteModels.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews row = new RemoteViews(ctxt.getPackageName(),
                    R.layout.item_widget);

            String wordName = recentFavouriteModels.get(position).getWordName();
            String partOfSpeech = recentFavouriteModels.get(position).getPartOfSpeech();

            row.setTextViewText(R.id.tvWord, wordName);
            row.setTextViewText(R.id.tvPartOfSpeech, partOfSpeech);

            return row;
        }

        @Override
        public RemoteViews getLoadingView() {
            return (null);
        }

        @Override
        public int getViewTypeCount() {
            return (1);
        }

        @Override
        public long getItemId(int position) {
            return (position);
        }

        @Override
        public boolean hasStableIds() {
            return (true);
        }

        @Override
        public void onDataSetChanged() {
            recentFavouriteModels = SQLite.select()
                    .from(RecentFavouriteModel.class)
                    .where(RecentFavouriteModel_Table.modelType.eq(ModelType.RECENT))
                    .queryList();
        }
    }

}