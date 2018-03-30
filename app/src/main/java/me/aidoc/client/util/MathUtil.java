package me.aidoc.client.util;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import me.aidoc.client.base.MyApplication;
import me.aidoc.client.R;


public class MathUtil {

    //出生日期字符串转化成Date对象
    public static Date parse(String strDate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.parse(strDate);
    }

    //由出生日期获得年龄
    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth) age--;
            }else{
                age--;
            }
        }
        return age;
    }

    /**
     * 能量消耗提示语
     * @return
     */
    public static String getEnergyConsusStr(Context context, String energy){
        // TODO 计算热量
        String first= MyApplication.getContext().getText(R.string.energy_consume).toString();
        String end=MyApplication.getContext().getText(R.string.energy_share).toString();
//        energy="555.00";
        try {
            float energyInt = Float.parseFloat(energy);
            if(energyInt==0) {
                return ""+MyApplication.getContext().getText(R.string.energy_no);
            }else if(energyInt<=44){// 果汁
                return ""+first+IntToSmallChineseNumber.ToCH(1)+MyApplication.getContext().getText(R.string.energy_1)+end;
            }else if(energyInt>44 && energyInt<=90){//按照火鸡腿计算
                return ""+first+IntToSmallChineseNumber.ToCH(1)+MyApplication.getContext().getText(R.string.energy_2)+end;
            }else if(energyInt>90 && energyInt<=120) {//按照哈根达斯计算
                return first+context.getString(R.string.one_root)+MyApplication.getContext().getText(R.string.energy_3)+end;
            }else if(energyInt>120){
                String multipleStr="";
                float multiple =energyInt/120;
                float remainder=energyInt%120;
                if(remainder<60){
                    multipleStr= IntToSmallChineseNumber.ToCH((int)multiple)+context.getString(R.string.helf_root);
                }else{
                    multiple++;
                    multipleStr=""+IntToSmallChineseNumber.ToCH((int)multiple)+context.getString(R.string.root_1);
                }
                return first+multipleStr+MyApplication.getContext().getText(R.string.energy_3)+end;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ""+MyApplication.getContext().getText(R.string.energy_no);
    }
}
