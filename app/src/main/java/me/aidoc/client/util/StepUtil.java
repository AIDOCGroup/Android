package me.aidoc.client.util;

import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import me.aidoc.client.base.MyApplication;
import me.aidoc.client.util.step.bean.TodayStep;
import me.aidoc.client.entity.resp.ArchivesResp;
import me.aidoc.client.entity.resp.FormulaResp;
import me.aidoc.client.util.step.bean.StepData;

public class StepUtil {
    public static StepData getLastStep() {
        List<StepData> list = DataUtil.getInstance(MyApplication.getContext()).getDataStorage().loadAll(StepData.class);
        if (list.size() > 0)
            return list.get(0);
        else return null;
    }

    public static List<StepData> getAllStep() {
        List<StepData> list = DataUtil.getInstance(MyApplication.getContext()).getDataStorage().loadAll(StepData.class);
        return list;
    }

    public static void saveStep(StepData userResp) {
        DataUtil.getInstance(MyApplication.getContext()).saveOrUpdate(userResp);
    }

    public static void deleteAllStep() {
        DataUtil.getInstance(MyApplication.getContext()).deleteAll();
    }

    /**
     * 保存初始化的今日步数，用于计步芯片
     */
    public static void saveInitTodayStep(int step){
        long time = new Date().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        TodayStep todayStep=new TodayStep(date,time,step);
        DataUtil.getInstance(MyApplication.getContext()).saveOrUpdate(todayStep);
    }

    /**
     * 获得初始化今日步数
     * @return
     */
    public static TodayStep getInitTodayStep(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String date = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳
        return  DataUtil.getInstance(MyApplication.getContext()).load(TodayStep.class,date);
    }


    /**
     * 今天是否保存了芯片初始步数
     * @return
     */
    public static boolean isInitTodaySaved(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        String nowDate = df.format(new Date());// new Date()为获取当前系统时间，也可使用当前时间戳

        TodayStep initTodayStep = getInitTodayStep();
        if(initTodayStep==null || nowDate==null) return false;
        if(initTodayStep.getDate().equals(nowDate)){
            // 今天保存过
            return true;
        }else{
            return false;
        }
    }

    /**
     * 当前日期记步
     *
     * @param key 日期
     * @return
     */
    public static StepData getCurrentStep(String key) {
        Object load = DataUtil.getInstance(MyApplication.getContext()).load(StepData.class, key);
        return load == null ? null : (StepData) load;
    }


    public static String getEnengy(Context context, int steps) {
        FormulaResp formulaResp = DataUtil.getInstance(context).load(FormulaResp.class, MyConstants.FORMULA);
        String formula = "weight*step*0.5*1.036";
        if (formulaResp != null) {
            formula = formulaResp.getFormula();
        }
        // 开始计算
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("rhino");
        int weight = 60;
        ArchivesResp archive = UserUtil.getArchives();
        if (archive != null && archive.getWeight() != 0) {
            weight = archive.getWeight();
        }
        String evalStr = formula.replaceAll("weight", "" + weight).replaceAll("step", "" + steps);
        try {
            Object eval = jse.eval(evalStr);
            double aDouble = Double.parseDouble(eval.toString());
            String energyStr;
            if (aDouble > 99 * 1000) {
                energyStr = "" + (int) (aDouble / 1000);
            } else {
                energyStr = String.format("%.2f", aDouble / 1000);
            }
            return energyStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
    }

    public static String getDistance(int steps) {
        double d = steps * (0.5) / 1000;
        String s = String.format("%.2f", d);
        return s;
    }

    /**
     * 获取当天日期
     *
     * @return
     */
    public static  String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

}
