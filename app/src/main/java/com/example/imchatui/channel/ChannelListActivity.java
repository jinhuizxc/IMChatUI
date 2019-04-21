package com.example.imchatui.channel;

import android.content.Intent;
import android.view.MenuItem;

import com.example.imchatui.R;
import com.example.imchatui.base.BaseActivity;


public class ChannelListActivity extends BaseActivity {

    @Override
    protected int menu() {
        return R.menu.channel_list;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.create) {
            createChannel();
            return true;
        } else if (item.getItemId() == R.id.subscribe) {
            subscribe();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int contentLayout() {
        return R.layout.fragment_container_activity;
    }

    @Override
    protected void afterViews() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.containerFrameLayout, new ChannelListFragment())
                .commit();
    }

    // TODO
    void createChannel() {
//        Intent intent = new Intent(this, CreateChannelActivity.class);
//        startActivity(intent);
//        finish();
    }

    // TODO
    void subscribe() {
//        Intent intent = new Intent(this, SearchChannelActivity.class);
//        startActivity(intent);
    }
}
