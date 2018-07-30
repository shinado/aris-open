/*
 * Copyright (C) 2014 singwhatiwanna(浠荤帀鍒� <singwhatiwanna@gmail.com>
 *
 * collaborator:鐢板暩,瀹嬫�瀹�
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ryg.dynamicload;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;

import com.ryg.dynamicload.internal.DLPluginPackage;

public interface DLPlugin {

    void onCreate(Bundle savedInstanceState);

    void onStart();

    void onRestart();

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void attach(Activity proxyActivity, DLPluginPackage pluginPackage);

    void onSaveInstanceState(Bundle outState);

    void onNewIntent(Intent intent);

    void onRestoreInstanceState(Bundle savedInstanceState);

    boolean onTouchEvent(MotionEvent event);

    boolean onKeyDown(int keyCode, KeyEvent event);

    void onWindowAttributesChanged(LayoutParams params);

    void onWindowFocusChanged(boolean hasFocus);

    void onBackPressed();

    boolean onCreateOptionsMenu(Menu menu);

    boolean onOptionsItemSelected(MenuItem item);

    void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults);

}
