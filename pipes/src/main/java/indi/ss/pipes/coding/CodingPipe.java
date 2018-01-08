package indi.ss.pipes.coding;

import android.util.TypedValue;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ss.aris.open.console.InputCallback;
import com.ss.aris.open.console.impl.DeviceConsole;
import com.ss.aris.open.console.text.TypingOption;
import com.ss.aris.open.pipes.action.DefaultInputActionPipe;
import com.ss.aris.open.pipes.entity.Pipe;
import com.ss.aris.open.pipes.entity.SearchableName;

/**
 * You're now in coding mode.
 * Author: ss
 * Created at 2017/10/30
 */
public class CodingPipe extends DefaultInputActionPipe {

    private int pointer = 0;

    private ScrollView scrollView;
    private TextView textView;

    private final static String CODE_START = "/**\n" +
            " * You're now in coding mode. \n" +
            " * Author: ss\n" +
            " * Created at 2017/10/30\n" +
            " */\n";

    private final static String CODE = "package com.notthiscompany.goodluck.home\n" +
            "\n" +
            "import android.app.*\n" +
            "import android.content.Intent\n" +
            "import android.os.Bundle\n" +
            "import android.os.Handler\n" +
            "import android.support.v4.view.PagerAdapter\n" +
            "import android.support.v4.view.ViewPager\n" +
            "import android.util.SparseArray\n" +
            "import android.view.LayoutInflater\n" +
            "import android.view.View\n" +
            "import android.view.ViewGroup\n" +
            "import android.widget.TextView\n" +
            "import android.widget.Toast\n" +
            "import com.android.volley.Response\n" +
            "import kotlinx.android.synthetic.main.*\n" +
            "import org.json.JSONObject\n" +
            "import java.text.SimpleDateFormat\n" +
            "import java.util.*\n" +
            "\n" +
            "class HomeFragment : BaseFragment() {\n" +
            "\n" +
            "  var isLive: Boolean = false\n" +
            "  var om: OrientationManager? = null\n" +
            "  var today: Calendar = Calendar.getInstance()\n" +
            "  var liveFlag: Int = 0\n" +
            "  val MAX = 10000\n" +
            "  var calendarAdjustment = 0\n" +
            "  lateinit var WEEKDAYS: Array<String>\n" +
            "  lateinit var resultView: ResultsView\n" +
            "  var cal: Calendar? = null\n" +
            "  var liveFragment: ILiveView? = null\n" +
            "\n" +
            "  override fun onCreateView(\n" +
            "    inflater: LayoutInflater?,\n" +
            "    container: ViewGroup?,\n" +
            "    savedInstanceState: Bundle?): View? {\n" +
            "    return inflater?.inflate(\n" +
            "      R.layout.fragment_home,\n" +
            "      container, false)\n" +
            "  }\n" +
            "\n" +
            "  override fun onViewCreated(\n" +
            "      view: View?, \n" +
            "      savedInstanceState: Bundle?) {\n" +
            "    super.onViewCreated(\n" +
            "        view, savedInstanceState)\n" +
            "\n" +
            "    WEEKDAYS = resources\n" +
            "        .getStringArray(R.array.weekday)\n" +
            "    resultView = ResultsView(\n" +
            "        view?.findViewById(\n" +
            "        R.id.results_fragment) as ViewGroup)\n" +
            "\n" +
            "    initWidgets()\n" +
            "    resetMenus()\n" +
            "\n" +
            "    object : Thread(){\n" +
            "      override fun run(){\n" +
            "        while (!isDestroyed){\n" +
            "          activity.runOnUiThread {\n" +
            "            loadDrawCloseTime()\n" +
            "          }\n" +
            "          sleep(60 * 1000)\n" +
            "        }\n" +
            "      }\n" +
            "    }.start()\n" +
            "  }\n" +
            "\n" +
            "  override fun onHiddenChanged(\n" +
            "        hidden: Boolean) {\n" +
            "    super.onHiddenChanged(hidden)\n" +
            "    log(\"hidden:\" + hidden)\n" +
            "    if (!hidden){\n" +
            "      resetMenus()\n" +
            "    }\n" +
            "  }\n" +
            "\n" +
            "  override fun onResume() {\n" +
            "    super.onResume()\n" +
            "    om?.onResume()\n" +
            "  }\n" +
            "\n" +
            "  override fun onPause() {\n" +
            "    super.onPause()\n" +
            "    om?.onPause()\n" +
            "  }\n" +
            "\n" +
            "  override fun onDestroyView() {\n" +
            "    super.onDestroyView()\n" +
            "    resultView.destroy()\n" +
            "  }\n" +
            "\n" +
            "  private fun resetMenus() {\n" +
            "    (activity as MainActivity).resetMenu(\n" +
            "        MenuItem(R.drawable.ic_refreshing,\n" +
            "            object : Callback {\n" +
            "          override fun callback(view: View) {\n" +
            "            refresh()\n" +
            "          }\n" +
            "        }))\n" +
            "  }\n" +
            "\n" +
            "  private fun setMenusOnLive() {\n" +
            "    (activity as MainActivity).resetMenu(\n" +
            "        MenuItem(R.drawable.ic_refreshing,\n" +
            "            object : Callback {\n" +
            "          override fun callback(view: View){\n" +
            "            refresh()\n" +
            "          }\n" +
            "        }),\n" +
            "        MenuItem(R.drawable.ic_close,\n" +
            "            object : Callback {\n" +
            "          override fun callback(view: View) {\n" +
            "            stopLive()\n" +
            "          }\n" +
            "        }))\n" +
            "  }\n" +
            "\n" +
            "  private fun initWidgets() {\n" +
            "    val listener = View.OnClickListener {\n" +
            "      view ->\n" +
            "      when (view.id) {\n" +
            "        R.id.live_btn -> {\n" +
            "          startLive()\n" +
            "        }\n" +
            "        R.id.btn_next ->\n" +
            "          if (date_vp.currentItem < MAX)\n" +
            "            date_vp.currentItem += 1\n" +
            "        R.id.btn_previous ->\n" +
            "          if (date_vp.currentItem > 0)\n" +
            "            date_vp.currentItem += -1\n" +
            "        R.id.share_btn -> startActivity(\n" +
            "            Intent(Intent.ACTION_SEND)\n" +
            "                .setType(\"text/plain\")\n" +
            "      }\n" +
            "    }\n" +
            "\n" +
            "    share_btn.setOnClickListener(listener)\n" +
            "    live_btn.setOnClickListener(listener)\n" +
            "    btn_previous.setOnClickListener(listener)\n" +
            "    btn_next.setOnClickListener(listener)\n" +
            "  }\n" +
            "\n" +
            "  private fun startYoutubeLive(ms: Int) {\n" +
            "    if (cal == null) {\n" +
            "      return\n" +
            "    }\n" +
            "\n" +
            "    val request = BaseStringRequest(\n" +
            "        \"\" + TimeHelper.formateTime(cal),\n" +
            "        \"\", Response.Listener {\n" +
            "      result: String ->\n" +
            "      if (isDestroyed) return@Listener\n" +
            "      try {\n" +
            "        val json = JSONObject(result)\n" +
            "        val url = json.getString(\"Url\")\n" +
            "        val id = url.replace(\"a\", \"\")\n" +
            "            .replace(\"b\", \"\")\n" +
            "            .replace(\"c\", \"\")\n" +
            "            .replace(\"d\", \"\")\n" +
            "        log(\"youtube: \" + id)\n" +
            "\n" +
            "        liveFragment?.onDestroy()\n" +
            "        startLive(YoutubeLiveView(activity,\n" +
            "            home_live_placeholder, id, ms))\n" +
            "      } catch(e: Exception) {\n" +
            "        e.printStackTrace()\n" +
            "      }\n" +
            "    })\n" +
            "\n" +
            "    DefaultApplication.getInstance()\n" +
            "        .addToRequestQueue(request)\n" +
            "  }\n" +
            "\n" +
            "  private fun startLive() {\n" +
            "    startLive(0)\n" +
            "  }\n" +
            "\n" +
            "  private fun startLive(ms: Int) {\n" +
            "    isLive = true\n" +
            "    setMenusOnLive()\n" +
            "\n" +
            "    if (isToday(cal) && liveFlag == 1) {\n" +
            "      log(\"rtmp: \")\n" +
            "      liveFragment?.onDestroy()\n" +
            "      startLive(RtmpLiveView(activity, \n" +
            "          home_live_placeholder, \"h\"))\n" +
            "    } else {\n" +
            "      startYoutubeLive(ms)\n" +
            "    }\n" +
            "  }\n" +
            "\n" +
            "  private fun startOrientationWatch(\n" +
            "      url: String, useYtb: Boolean) {\n" +
            "    om = OrientationManager(activity, {\n" +
            "      isPortrait ->\n" +
            "      if (!isPortrait)\n" +
            "        LargeVideoActivity.start(\n" +
            "            activity, url, useYtb)\n" +
            "    }, true)\n" +
            "    om!!.onResume()\n" +
            "  }\n" +
            "\n" +
            "  private fun stopOrientationWatch() {\n" +
            "    om?.onPause()\n" +
            "  }\n" +
            "\n" +
            "  private fun loadDrawCloseTime() {\n" +
            "    val dialog = ProgressDialog(activity)\n" +
            "    dialog.setCancelable(true)\n" +
            "    dialog.setMessage(getString(R.string.loading))\n" +
            "    dialog.show()\n" +
            "    val request = BaseStringRequest(\"w\", \"\",\n" +
            "        Response.Listener<String> {\n" +
            "          string ->\n" +
            "          if (isDestroyed) return@Listener\n" +
            "\n" +
            "          try {\n" +
            "            dialog.dismiss()\n" +
            "            val json = JSONObject(string)\n" +
            "            configurations.setDrawOpenTime(openTime)\n" +
            "            liveFlag = json.getInt(\"Live\")\n" +
            "\n" +
            "            getToday(json.getString(\"TodayDate\"))\n" +
            "\n" +
            "            resetCalendar()\n" +
            "            notifyLiveButton()\n" +
            "          } catch(e: Exception) {\n" +
            "          }\n" +
            "\n" +
            "        })\n" +
            "\n" +
            "    DefaultApplication.getInstance()\n" +
            "        .addToRequestQueue(request)\n" +
            "  }\n" +
            "\n" +
            "  private fun getToday(todayString: String?) {\n" +
            "    val date = SimpleDateFormat(\"yyyy-MM-dd\", \n" +
            "        Locale.getDefault()).parse(todayString)\n" +
            "    today.time = date\n" +
            "\n" +
            "    log(\"month: \" + today.get(Calendar.MONTH))\n" +
            "  }\n" +
            "\n" +
            "  private fun resetCalendar() {\n" +
            "    if (liveFlag == 0) {\n" +
            "//      if (!TimeHelper.isLater(closeTime)) {\n" +
            "      //not today\n" +
            "//        calendarAdjustment = -1\n" +
            "//      }\n" +
            "    }\n" +
            "    initDateWidget()\n" +
            "  }\n" +
            "\n" +
            "  private fun notifyLiveButton() {\n" +
            "    if (isToday(cal)) {\n" +
            "      if (liveFlag == 1) {\n" +
            "        live_btn.visibility = View.VISIBLE\n" +
            "        live_btn.setText(R.string.live)\n" +
            "      } else {\n" +
            "        getYoutubeUrl()\n" +
            "      }\n" +
            "    } else {\n" +
            "      live_btn.visibility = View.VISIBLE\n" +
            "      live_btn.setText(R.string.replay)\n" +
            "    }\n" +
            "  }\n" +
            "\n" +
            "  private fun getYoutubeUrl() {\n" +
            "    val url = \"url\" + \n" +
            "        TimeHelper.formateTime(cal)\n" +
            "\n" +
            "    val request = BaseStringRequest(url, \"\", \n" +
            "        Response.Listener {\n" +
            "      result: String ->\n" +
            "      try {\n" +
            "        if (isDestroyed) return@Listener\n" +
            "\n" +
            "        val json = JSONObject(result)\n" +
            "        if (json.getString(\"Url\") != null) {\n" +
            "          live_btn.visibility = View.VISIBLE\n" +
            "          live_btn.setText(R.string.replay)\n" +
            "        } else {\n" +
            "          live_btn.visibility = View.GONE\n" +
            "        }\n" +
            "      } catch(e: Exception) {\n" +
            "        e.printStackTrace()\n" +
            "        live_btn.visibility = View.GONE\n" +
            "      }\n" +
            "    })\n" +
            "\n" +
            "    DefaultApplication.getInstance()\n" +
            "        .addToRequestQueue(request)\n" +
            "  }\n" +
            "\n" +
            "  private fun isToday(cal: Calendar?): Boolean {\n" +
            "    return TimeHelper.sameDay(cal, today)\n" +
            "  }\n" +
            "\n" +
            "  private fun startLive(fragment: ILiveView) {\n" +
            "    stopOrientationWatch()\n" +
            "    val isYoutube = fragment is YoutubeLiveView\n" +
            "    startOrientationWatch(fragment.url, isYoutube)\n" +
            "    liveFragment = fragment\n" +
            "  }\n" +
            "\n" +
            "  fun stopLive() {\n" +
            "    isLive = false\n" +
            "    stopOrientationWatch()\n" +
            "    resetMenus()\n" +
            "    home_live_placeholder.visibility = View.GONE\n" +
            "    liveFragment?.onDestroy()\n" +
            "    liveFragment = null\n" +
            "  }\n" +
            "\n" +
            "  private fun initDateWidget() {\n" +
            "    date_vp.adapter = object : PagerAdapter() {\n" +
            "\n" +
            "      val views = SparseArray<View>()\n" +
            "\n" +
            "      override fun isViewFromObject(\n" +
            "          view: View?, obj: Any?): Boolean\n" +
            "          = view === obj\n" +
            "\n" +
            "      override fun getCount(): Int = \n" +
            "          MAX + 1 + calendarAdjustment\n" +
            "\n" +
            "      override fun instantiateItem(\n" +
            "          container: ViewGroup?, position: Int): Any {\n" +
            "        val v = LayoutInflater.from(activity)\n" +
            "            .inflate(R.layout.item_home_date,\n" +
            "                container, false)\n" +
            "        setView(position, v)\n" +
            "        views.put(position, v)\n" +
            "        container?.addView(v)\n" +
            "        return v\n" +
            "      }\n" +
            "\n" +
            "      override fun destroyItem(\n" +
            "          container: ViewGroup?, \n" +
            "          position: Int, obj: Any?) {\n" +
            "        container?.removeView(views.get(position))\n" +
            "      }\n" +
            "\n" +
            "      fun setView(position: Int, view: View) {\n" +
            "        val cal = Calendar.getInstance()\n" +
            "        cal.timeInMillis = today.timeInMillis\n" +
            "        cal.add(Calendar.DATE, position - MAX)\n" +
            "\n" +
            "        val dateTv = view\n" +
            "            .findViewById(R.id.date_tv) \n" +
            "            as TextView\n" +
            "        val weekdayTv = view\n" +
            "            .findViewById(R.id.day_tv) \n" +
            "            as TextView\n" +
            "\n" +
            "        val year = cal.get(Calendar.YEAR)\n" +
            "        val month = cal.get(Calendar.MONTH) + 1\n" +
            "        val day = cal.get(Calendar.DAY_OF_MONTH)\n" +
            "        val weekday = cal.get(Calendar.DAY_OF_WEEK)\n" +
            "\n" +
            "        dateTv.text = \"$day/$month/$year\"\n" +
            "        weekdayTv.text = WEEKDAYS[weekday]\n" +
            "\n" +
            "        view.setOnClickListener {\n" +
            "          _->\n" +
            "          val datePickerListener \n" +
            "              = DatePickerDialog\n" +
            "              .OnDateSetListener {\n" +
            "            _, selectedYear, \n" +
            "            selectedMonth, selectedDay ->\n" +
            "            @Suppress(\"NAME_SHADOWING\")\n" +
            "            val cal = Calendar.getInstance()\n" +
            "\n" +
            "            cal.set(Calendar.YEAR, selectedYear)\n" +
            "            cal.set(Calendar.MONTH, selectedMonth)\n" +
            "            cal.set(Calendar.DAY_OF_MONTH, d-1)\n" +
            "\n" +
            "            val diff = TimeHelper.diff(cal, today)\n" +
            "            if (diff <= 0 && diff >= -MAX) {\n" +
            "              date_vp.currentItem= MAX + \n" +
            "                  calendarAdjustment + diff\n" +
            "            }\n" +
            "          }\n" +
            "\n" +
            "          val datePicker = DatePickerDialog(activity,\n" +
            "              datePickerListener,\n" +
            "              today.get(Calendar.YEAR),\n" +
            "              today.get(Calendar.MONTH),\n" +
            "              today.get(Calendar.DAY_OF_MONTH))\n" +
            "\n" +
            "          val maxDate = Calendar.getInstance()\n" +
            "          maxDate.timeInMillis = today.timeInMillis\n" +
            "          maxDate.add(Calendar.DATE, calendarAdjustment)\n" +
            "          datePicker.setCancelable(true)\n" +
            "          datePicker.show()\n" +
            "        }\n" +
            "\n" +
            "      }\n" +
            "    }\n" +
            "    date_vp.addOnPageChangeListener(\n" +
            "        object : ViewPager.OnPageChangeListener {\n" +
            "      override fun onPageScrollStateChanged(state: Int) {\n" +
            "      }\n" +
            "\n" +
            "      override fun onPageScrolled(\n" +
            "          position: Int, \n" +
            "          positionOffset: Float, \n" +
            "          positionOffsetPixels: Int) {\n" +
            "      }\n" +
            "\n" +
            "      override fun onPageSelected(position: Int) {\n" +
            "        cal = Calendar.getInstance()\n" +
            "        cal!!.timeInMillis = today.timeInMillis\n" +
            "        cal!!.add(Calendar.DATE, position - MAX)\n" +
            "        resultView.loadResult(cal)\n" +
            "\n" +
            "        if (isLive) {\n" +
            "          startLive()\n" +
            "        }\n" +
            "        notifyLiveButton()\n" +
            "\n" +
            "        if (position == MAX + calendarAdjustment) {\n" +
            "          btn_next.visibility = View.GONE\n" +
            "        } else {\n" +
            "          btn_next.visibility = View.VISIBLE\n" +
            "        }\n" +
            "\n" +
            "      }\n" +
            "    })\n" +
            "\n" +
            "    Handler().postDelayed({\n" +
            "      date_vp?.currentItem = MAX + calendarAdjustment\n" +
            "    }, 200)\n" +
            "  }\n" +
            "\n" +
            "  fun refresh() {\n" +
            "    Toast.makeText(activity, \n" +
            "        R.string.refreshing, \n" +
            "        Toast.LENGTH_LONG).show()\n" +
            "    loadDrawCloseTime()\n" +
            "//    resultView.loadResult(cal)\n" +
            "  }\n" +
            "\n" +
            "  override fun onActivityResult(\n" +
            "      requestCode: Int, resultCode: Int, data: Intent?) {\n" +
            "    log(\"onActivityResult$requestCode, $resultCode\")\n" +
            "    if (resultCode == Activity.RESULT_OK) {\n" +
            "      val ms = data?.getIntExtra(\"ms\", 0) ?: 0\n" +
            "      if (isLive) startLive(ms)\n" +
            "    }\n" +
            "  }\n" +
            "\n" +
            "}";

    public CodingPipe(int id) {
        super(id);
    }

    @Override
    public String getDisplayName() {
        return "$imCoding";
    }

    @Override
    public SearchableName getSearchable() {
        return new SearchableName("im", "coding");
    }

    @Override
    public void onParamsEmpty(Pipe rs, OutputCallback callback) {
        letsGo(callback);
    }

    @Override
    public void onParamsNotEmpty(Pipe rs, OutputCallback callback) {
        letsGo(callback);
    }

    private void letsGo(OutputCallback callback) {
        DeviceConsole console = (DeviceConsole) getConsole();
        if (callback == getConsoleCallback()) {
            console.blindMode();
            console.addInputCallback(mInputCallback);

            DeviceConsole deviceConsole = (DeviceConsole) getConsole();
            textView = new TextView(context, null);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                    deviceConsole.getDisplayTextView().getTextSize());
            textView.setTextColor(deviceConsole.getDisplayTextView()
                    .getCurrentTextColor());
            textView.setTypeface(deviceConsole.getTypeface());

            textView.setText(CODE_START);

            scrollView = new ScrollView(context, null);
            scrollView.addView(textView);
            console.replaceCurrentView(scrollView);
        } else {
            callback.onOutput("shh~I am coding");
        }
    }

    @Override
    public void acceptInput(Pipe result, String input, Pipe.PreviousPipes previous, OutputCallback callback) {

    }

    private InputCallback mInputCallback = new InputCallback() {
        @Override
        public void onInput(String character) {
            DeviceConsole console = (DeviceConsole) getConsole();
            if ("#".equals(character)) {
                console.quitBlind();
                console.removeInputCallback(mInputCallback);
                console.reshowTerminal();
            } else {
                if (pointer >= CODE.length()) pointer = 0;
                String random = CODE.substring(pointer, Math.min(CODE.length(),
                        pointer += getRandom(8, 8)));

                textView.append(random);
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
//                getConsole().input(random, null, TypingOption.appendString(), null);
            }
        }
    };

    /**
     * get a random number
     *
     * @param size   e.g. 3: {0,1,2}
     * @param offset e.g. 1:{1,2,3}, -1:{-1,0,1}
     * @return random + offset
     */
    private int getRandom(int size, int offset) {
        int random = ((int) System.currentTimeMillis() % size);
        random = Math.abs(random);
        random += offset;
        return random;
    }

}
