package me.aidoc.client.parser;

import com.zhuoting.health.ProtocolTag;
import com.zhuoting.health.bean.BloodInfo;
import com.zhuoting.health.bean.HeartInfo;
import com.zhuoting.health.bean.SleepInfo;
import com.zhuoting.health.bean.SportInfo;
import com.zhuoting.health.notify.IDataResponse;
import com.zhuoting.health.notify.IErrorCommand;
import com.zhuoting.health.notify.IRequestResponse;
import com.zhuoting.health.util.DataUtil;
import com.zhuoting.health.util.TransUtils;

import java.util.ArrayList;

/**
 * Created by Hqs on 2018/7/17
 */
//public class RealTimeDataParser implements IProtocolParser {
//    public IRequestResponse mIRequestResponse;
//    public IErrorCommand mIErrorCommand;
//    public IDataResponse mIDataResponse;
//    private byte[] bytes;
//
//    private RealTimeDataParser() {
//    }
//
//    private static RealTimeDataParser mRealTimeDataParser;
//
//    public static RealTimeDataParser newInstance() {                     // 单例模式，双重锁
//        if (mRealTimeDataParser == null) {
//            synchronized (RealTimeDataParser.class) {
//                if (mRealTimeDataParser == null) {
//                    mRealTimeDataParser = new RealTimeDataParser();
//                }
//            }
//        }
//        return mRealTimeDataParser;
//    }
//
//    @Override
//    public int findDataType(byte[] data) {
//        return 0;
//    }
//
//    @Override
//    public void parseData(byte[] data) {
//        String dataStr = TransUtils.bytes2hex(data);
//        String tagHead = dataStr.substring(0, 2);
//        String tagStr = dataStr.substring(0, 4);
//        // 做了些优化
//        if (tagHead.equals("05")) {
//
//            if (tagStr.equals(ProtocolTag.SYNCHRO_ALL_SWITCH_RESPONSE)) {    // 同步所有开关
//
//                mIRequestResponse.onSynchronizdAllSwitchResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.BLOCK_COMFIRM_RESPONSE)) {         // BLOCK 确认信息响应
//
//                mIRequestResponse.onBlockConfirmResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_TODAY_SPORT_DATA)) {     // 同步今天的运动数据
//                if (data.length >= 20) {               // 如果字节数大于或者等于12个字节，则这条数据是运动的数据
//                    byte[] todaySport = new byte[]{data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14], data[15], data[16], data[17]};
//                    SportInfo sportInfo = new SportInfo();
//                    sportInfo.initWithData(todaySport);
//                    mIDataResponse.onTodaySport(sportInfo);
//                } else {                                             // 如果字节数小于12就说明这是一条数据数目的数据
//                    byte[] bytesRecord = new byte[]{data[5], data[4]};
//                    int recordNum = Integer.valueOf(DataUtil.byteToHexString(bytesRecord), 16);
//                    if (recordNum == 0) {
//                        mIDataResponse.onSynchronizedTodaySport(recordNum, 0, 0);
//                    } else {
//                        byte[] bytesPack = new byte[]{data[9], data[8], data[7], data[6]};
//                        byte[] bytesAll = new byte[]{data[13], data[12], data[11], data[10]};
//                        int packNum = Integer.valueOf(DataUtil.byteToHexString(bytesPack), 16);  // 封包数
//                        int allNum = Integer.valueOf(DataUtil.byteToHexString(bytesAll), 16);         // 总包数
//                        mIDataResponse.onSynchronizedTodaySport(recordNum, packNum, allNum);
//                    }
//                }
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_HISTORY_SPORT_DATA)) { // 同步历史的运动数据
//
//                if (data.length >= 20) {               // 如果字节数大于或者等于12个字节，则这条数据是运动的数据
//                    byte[] todaySport = new byte[]{data[4], data[5], data[6], data[7], data[8], data[9], data[10], data[11], data[12], data[13], data[14], data[15], data[16], data[17]};
//                    SportInfo sportInfo = new SportInfo();
//                    sportInfo.initWithData(todaySport);
//                    mIDataResponse.onHistorySport(sportInfo);
//                } else {                                             // 如果字节数小于12就说明这是一条数据数目的数据
//                    byte[] bytesRecord = new byte[]{data[5], data[4]};
//                    int recordNum = Integer.valueOf(DataUtil.byteToHexString(bytesRecord), 16);
//                    if (recordNum == 0) {
//                        mIDataResponse.onSynchronizedHistorySport(recordNum, 0, 0);
//                    } else {
//                        byte[] bytesPack = new byte[]{data[9], data[8], data[7], data[6]};
//                        byte[] bytesAll = new byte[]{data[13], data[12], data[11], data[10]};
//                        int packNum = Integer.valueOf(DataUtil.byteToHexString(bytesPack), 16);  // 封包数
//                        int allNum = Integer.valueOf(DataUtil.byteToHexString(bytesAll), 16);         // 总包数
//                        mIDataResponse.onSynchronizedHistorySport(recordNum, packNum, allNum);
//                    }
//                }
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_TODAY_SLEEP_DATA)) {   // 同步今天的睡眠数据
//
//                if (data.length == 16) {              // 如果只有16个字节，那说明这条数据是记录条数的数据
//                    byte[] bytesRecord = new byte[]{data[5], data[4]};
//                    int recordNum = Integer.valueOf(DataUtil.byteToHexString(bytesRecord), 16);
//                    if (recordNum == 0) {
//                        mIDataResponse.onSynchronizedTodaySleep(recordNum, 0, 0);
//                    } else {
//                        byte[] bytesPack = new byte[]{data[9], data[8], data[7], data[6]};
//                        byte[] bytesAll = new byte[]{data[13], data[12], data[11], data[10]};
//                        int packNum = Integer.valueOf(DataUtil.byteToHexString(bytesPack), 16);  // 封包数
//                        int allNum = Integer.valueOf(DataUtil.byteToHexString(bytesAll), 16);         // 总包数
//                        mIDataResponse.onSynchronizedTodaySleep(recordNum, packNum, allNum);
//                    }
//                } else {
//                    SleepInfo sleepInfo = new SleepInfo();
//                    sleepInfo.initWithData(data);
//                    mIDataResponse.onTodaySleep(sleepInfo);
//                }
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_HISTORY_SLEEP_DATA)) { // 同步历史睡眠数据
//
//                if (data.length == 16) {              // 如果只有16个字节，那说明这条数据是记录条数的数据
//                    byte[] bytesRecord = new byte[]{data[5], data[4]};
//                    int recordNum = Integer.valueOf(DataUtil.byteToHexString(bytesRecord), 16);
//                    if (recordNum == 0) {
//                        mIDataResponse.onSynchronizedHistorySleep(recordNum, 0, 0);
//                    } else {
//                        byte[] bytesPack = new byte[]{data[9], data[8], data[7], data[6]};
//                        byte[] bytesAll = new byte[]{data[13], data[12], data[11], data[10]};
//                        int packNum = Integer.valueOf(DataUtil.byteToHexString(bytesPack), 16);  // 封包数
//                        int allNum = Integer.valueOf(DataUtil.byteToHexString(bytesAll), 16);         // 总包数
//                        mIDataResponse.onSynchronizedHistorySleep(recordNum, packNum, allNum);
//                    }
//                } else {
//                    SleepInfo sleepInfo = new SleepInfo();
//                    sleepInfo.initWithData(data);
//                    mIDataResponse.onHistorySleep(sleepInfo);
//                }
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_TODAY_HEART_RATE_DATA)) {    // 同步今天的心率数据
//
//                if (data.length == 16) {              // 如果只有16个字节，那说明这条数据是记录条数的数据
//                    byte[] bytesRecord = new byte[]{data[5], data[4]};
//                    int recordNum = Integer.valueOf(DataUtil.byteToHexString(bytesRecord), 16);
//                    if (recordNum == 0) {
//                        mIDataResponse.onSynchronizedTodayHeartRate(recordNum, 0, 0);
//                    } else {
//                        byte[] bytesPack = new byte[]{data[9], data[8], data[7], data[6]};
//                        byte[] bytesAll = new byte[]{data[13], data[12], data[11], data[10]};
//                        int packNum = Integer.valueOf(DataUtil.byteToHexString(bytesPack), 16);  // 封包数
//                        int allNum = Integer.valueOf(DataUtil.byteToHexString(bytesAll), 16);         // 总包数
//                        mIDataResponse.onSynchronizedTodayHeartRate(recordNum, packNum, allNum);
//                    }
//                } else {
//                    HeartInfo heartInfo = new HeartInfo();
//                    byte[] heartRateToday = new byte[data.length - 6];
//                    for (int i = 0; i < heartRateToday.length; i++) {
//                        heartRateToday[i] = data[i + 4];
//                    }
//                    heartInfo.initWithData(heartRateToday);
//                    mIDataResponse.onTodayHeartRate(heartInfo);
//                }
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_HISTORY_HEART_RATE_DATA)) { // 同步历史心率数据
//
//                if (data.length == 16) {              // 如果只有16个字节，那说明这条数据是记录条数的数据
//                    byte[] bytesRecord = new byte[]{data[5], data[4]};
//                    int recordNum = Integer.valueOf(DataUtil.byteToHexString(bytesRecord), 16);
//                    if (recordNum == 0) {
//                        mIDataResponse.onSynchronizedHistoryHeartRate(recordNum, 0, 0);
//                    } else {
//                        byte[] bytesPack = new byte[]{data[9], data[8], data[7], data[6]};
//                        byte[] bytesAll = new byte[]{data[13], data[12], data[11], data[10]};
//                        int packNum = Integer.valueOf(DataUtil.byteToHexString(bytesPack), 16);  // 封包数
//                        int allNum = Integer.valueOf(DataUtil.byteToHexString(bytesAll), 16);         // 总包数
//                        mIDataResponse.onSynchronizedHistoryHeartRate(recordNum, packNum, allNum);
//                    }
//                } else {
//                    HeartInfo heartInfo = new HeartInfo();
//                    byte[] heartRateToday = new byte[data.length - 6];
//                    for (int i = 0; i < heartRateToday.length; i++) {
//                        heartRateToday[i] = data[i + 4];
//                    }
//                    heartInfo.initWithData(heartRateToday);
//                    mIDataResponse.onHistoryHeartRate(heartInfo);
//                }
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_TODAY_BLOOD_PRESSURE_DATA)) {    // 同步今天的血压数据
//
//                if (data.length == 16) {              // 如果只有16个字节，那说明这条数据是记录条数的数据
//                    byte[] bytesRecord = new byte[]{data[5], data[4]};
//                    int recordNum = Integer.valueOf(DataUtil.byteToHexString(bytesRecord), 16);
//                    if (recordNum == 0) {
//                        mIDataResponse.onSynchronizedTodayBloodPressure(recordNum, 0, 0);
//                    } else {
//                        byte[] bytesPack = new byte[]{data[9], data[8], data[7], data[6]};
//                        byte[] bytesAll = new byte[]{data[13], data[12], data[11], data[10]};
//                        int packNum = Integer.valueOf(DataUtil.byteToHexString(bytesPack), 16);  // 封包数
//                        int allNum = Integer.valueOf(DataUtil.byteToHexString(bytesAll), 16);         // 总包数
//                        mIDataResponse.onSynchronizedTodayBloodPressure(recordNum, packNum, allNum);
//                    }
//                } else {
//                    BloodInfo bloodInfo = new BloodInfo();
//                    byte[] heartRateToday = new byte[data.length - 6];
//                    for (int i = 0; i < heartRateToday.length; i++) {
//                        heartRateToday[i] = data[i + 4];
//                    }
//                    bloodInfo.initWithData(heartRateToday);
//                    mIDataResponse.onTodayBloodPressure(bloodInfo);
//                }
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_HISTORY_BLOOD_PRESSURE_DATA)) { // 同步历史血压数据
//
//                if (data.length == 16) {              // 如果只有16个字节，那说明这条数据是记录条数的数据
//                    byte[] bytesRecord = new byte[]{data[5], data[4]};
//                    int recordNum = Integer.valueOf(DataUtil.byteToHexString(bytesRecord), 16);
//                    if (recordNum == 0) {
//                        mIDataResponse.onSynchronizedHistoryBloodPressure(recordNum, 0, 0);
//                    } else {
//                        byte[] bytesPack = new byte[]{data[9], data[8], data[7], data[6]};
//                        byte[] bytesAll = new byte[]{data[13], data[12], data[11], data[10]};
//                        int packNum = Integer.valueOf(DataUtil.byteToHexString(bytesPack), 16);  // 封包数
//                        int allNum = Integer.valueOf(DataUtil.byteToHexString(bytesAll), 16);         // 总包数
//                        mIDataResponse.onSynchronizedHistoryBloodPressure(recordNum, packNum, allNum);
//                    }
//                } else {
//                    BloodInfo bloodInfo = new BloodInfo();
//                    byte[] heartRateToday = new byte[data.length - 6];
//                    for (int i = 0; i < heartRateToday.length; i++) {
//                        heartRateToday[i] = data[i + 4];
//                    }
//                    bloodInfo.initWithData(heartRateToday);
//                    mIDataResponse.onHistoryBloodPressure(bloodInfo);
//                }
//
//            } else if (tagStr.equals(ProtocolTag.DELETE_SPORT_DATA)) {                    // 删除运动数据
//
//                mIRequestResponse.onDeleteSportData(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.DELETE_SLEEP_DATA)) {                    // 删除睡眠数据
//
//                mIRequestResponse.onDeleteSleepData(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.DELETE_HEART_RATE_DATA)) {     // 删除心率数据
//
//                mIRequestResponse.onDeleteHeartRateData(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.DELETE_BLOOD_PRESSURE_DATA)) {   //删除血压数据
//
//                mIRequestResponse.onDeleteBloodPressureData(data[4]);
//
//            }
//
//        } else if (tagHead.equals("06")) {            // 不断上传，不需要回应
//
//            // 同步步数、距离、卡路里
//            if (tagStr.equals(ProtocolTag.SYNCHRO_STEP_DISTANCE_CALORIE_WITHOUT_RES)) {
//
//                if (bytes == null) ;
//                bytes = new byte[2];
//                bytes[0] = data[5];
//                bytes[1] = data[4];
//                int steps = Integer.valueOf(DataUtil.byteToHexString(bytes), 16);
//                bytes[0] = data[7];
//                bytes[1] = data[6];
//                int distance = Integer.valueOf(DataUtil.byteToHexString(bytes), 16);
//                bytes[0] = data[9];
//                bytes[1] = data[10];
//                int calories = Integer.valueOf(DataUtil.byteToHexString(bytes), 16);
//                mIDataResponse.onRealTimeSportData(steps, distance, calories);
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_HEART_RATE_WITHOUT_RES)) {        // 心率
//
//                int heartRate = Integer.valueOf(dataStr.substring(8, 10), 16);
//                mIDataResponse.onRealTimeHeartRate(heartRate);
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_BLOOD_OXYGEN_WITHOUT_RES)) {  // 血氧
//
//                int bloodOxygen = Integer.valueOf(dataStr.substring(8, 10), 16);
//                mIDataResponse.onRealTimeOxygen(bloodOxygen);
//
//            } else if (tagStr.equals(ProtocolTag.SYNCHRO_PRESSURE_HEART_RATE_WITHOUT_RES)) { // 同步血压和心率
//
//                int systolic = Integer.valueOf(dataStr.substring(8, 10), 16);
//                int diastolic = Integer.valueOf(dataStr.substring(10, 12), 16);
//                int heartRate = Integer.valueOf(dataStr.substring(12, 14), 16);
//                mIDataResponse.onRealTimeBloodPressure(systolic, diastolic, heartRate);
//
//            } else if (tagStr.equals(ProtocolTag.OPTOELECTRONIC_WAVEFORM)) {              //  光电波形
//
//                mIDataResponse.onOptoelectronic(data);
//
//            } else if (tagStr.equals(ProtocolTag.ELECTROCARDIOGRAM)) {                                // 心电波形
//
//                mIDataResponse.onElectrocardiogram(data);
//
//            }
//
//        } else if (tagHead.equals("01")) {            // 01 开头 各种基础设置
//
//            if (tagStr.equalsIgnoreCase(ProtocolTag.TIME_SETTING)) {                                   // 时间设置
//
//                if (isErrorType(data)) {                     // 校验错误，估计不会执行到这里
//                    mIErrorCommand.onErrorCommand(ProtocolTag.TIME_SETTING, data[4]);
//                } else {                                                    // 时间设置
//                    mIRequestResponse.onTimeSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.ALARM_CLOCK_SETTING)) {    // 闹钟提醒
//
//                if (isErrorType(data)) {             //如果发送命令发生错误
//                    mIErrorCommand.onErrorCommand(ProtocolTag.ALARM_CLOCK_SETTING, data[4]);
//                } else {
//                    if (data[4] == 0) {              // 操作码为0
//                        if (data[6] == 0) {          // 已设置的闹钟,闹钟数为0
//                            mIDataResponse.onQueryAlarmClock(data[5], data[6], null);
//                        } else {                              // 闹钟数1个或一个以上
//                            int settingNum = data[6];
//                            ArrayList<byte[]> alarmList = new ArrayList<>();
//                            for (int i = 0; i < settingNum; i++) {
//                                alarmList.add(new byte[]{data[7 + i * 5], data[8 + i * 5], data[9 + i * 5], data[10 + i * 5], data[11 + i * 5]});
//                            }
//                            mIDataResponse.onQueryAlarmClock(data[5], data[6], alarmList);
//                        }
//                    } else if (data[4] == 1) {    // 操作码为1
//                        mIRequestResponse.onAlarmSettingResponse(data[5]);
//                    } else if (data[4] == 2) {  // 操作码为2
//                        mIRequestResponse.onDeleteAlarmSetting(data[5]);
//                    } else if (data[4] == 3) {   // 操作码为3
//                        mIRequestResponse.onModifyAlarmResponse(data[5]);
//                    }
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.TARGET_SETTING)) {                   // 目标设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.TARGET_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onTargetSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.USER_INFO_SETTING)) {            // 用户设置
//
//                if (isErrorType(data)) {                 // 发送命令错误
//                    mIErrorCommand.onErrorCommand(ProtocolTag.USER_INFO_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onUserInfoSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.UNIT_SETTING)) {                          // 单位设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.UNIT_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onUnitSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.LONG_SIT_SETTING)) {                // 久坐设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.LONG_SIT_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onLongsitSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.PREVENT_LOST_SETTING)) {    // 防丢失开关设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.PREVENT_LOST_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onPreventLostOnOffResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.PREVENT_LOST_PARAMS_SETTING)) {    // 防丢参数设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.PREVENT_LOST_PARAMS_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onPreventLostParamSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.LEFT_OR_RIGHT_HAND_SETTING)) { // 左手和右手设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.LEFT_OR_RIGHT_HAND_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onLeftOrRightHandSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.MOBILE_OS_SETTING)) {              // 手机系统设置（Android or IOS）
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.MOBILE_OS_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onMobileOSSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.NOTIFYCATION_ONOFF_SETTING)) {     // 通知提醒开关设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.NOTIFYCATION_ONOFF_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onNotificationSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.HEART_RATE_REMIND_SETTING)) {       // 心率提醒设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.HEART_RATE_REMIND_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onHeartRateAlarmSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.HEART_RATE_MONITOR)) {                          // 心率监测
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.HEART_RATE_MONITOR, data[4]);
//                } else {
//                    mIRequestResponse.onHeartRateMonitorResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.FIND_MOBILE_ONOFF)) {                              // 寻找手机开关
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.FIND_MOBILE_ONOFF, data[4]);
//                } else {
//                    mIRequestResponse.onFindMobileOnOffResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.RECOVER_TO_DEFAULT)) {                         // 恢复出厂设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.RECOVER_TO_DEFAULT, data[4]);
//                } else {
//                    mIRequestResponse.onRecoverToDefaultSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.DONOT_DISTURB)) {                                      // 免打扰设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.DONOT_DISTURB, data[4]);
//                } else {
//                    mIRequestResponse.onDisturbeSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.ANCS_ONOFF)) {                                              // ANCS 用不了，这是安卓系统
//
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.AEROBIC_EXERCISE_ONOFF)) {              // 有氧运动开关
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.AEROBIC_EXERCISE_ONOFF, data[4]);
//                } else {
//                    mIRequestResponse.onAerobicExerciseResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.LANGUAGE_SETTING)) {                             // 语言设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.LANGUAGE_SETTING, data[4]);
//                } else {
//                    mIRequestResponse.onLanguageSettingResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.LEFT_THE_WRIST_TO_BRIGHT)) {          // 抬腕亮屏
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.LEFT_THE_WRIST_TO_BRIGHT, data[4]);
//                } else {
//                    mIRequestResponse.onLeftTheWristToBrightResponse(data[4]);
//                }
//
//            } else if (tagStr.equalsIgnoreCase(ProtocolTag.BRIGHTNESS_CONTROL)) {                         // 亮度设置
//
//                if (isErrorType(data)) {
//                    mIErrorCommand.onErrorCommand(ProtocolTag.BRIGHTNESS_CONTROL, data[4]);
//                } else {
//                    mIRequestResponse.onBrightnessSettingResponse(data[4]);
//                }
//            }
//
//        } else {
//
//            if (tagStr.equals(ProtocolTag.FIRMWARE_UPDATE_THIRD)) {                          // 固件升级 第三方 Nordic 的 DFU
//
//                if (isErrorType(data)) {            //  错误码
//                    mIErrorCommand.onErrorCommand(ProtocolTag.FIRMWARE_UPDATE_THIRD, data[4]);
//                } else {                                            // 数据响应
//                    mIRequestResponse.onFirmWareUpdateResponse(data[4]);
//                }
//
//            } else if (tagStr.equals(ProtocolTag.FIRMWARE_UPDATE_LOCAL)) {               // 固件升级 使用自己定义的
//
//                if (data[4] == 0) {                         // argument 为 0
//                    if ((data[11]) == -1 && (data[12]) == -1) {                // 无下载固件
//                        mIDataResponse.onFirmWareInfoResponse(data[4], data[5] + data[6] * 0x100, data[7], data[8],
//                                data[9], data[10], data[11], data[12], 0);
//                    } else {
//                        byte[] length = new byte[]{data[16], data[15], data[14], data[13]};
//                        int bytesLength = Integer.valueOf(DataUtil.byteToHexString(length), 16);
//                        mIDataResponse.onFirmWareInfoResponse(data[4], data[5] + data[6] * 0x100, data[7], data[8],
//                                data[9], data[10], data[11], data[12], bytesLength);
//                    }
//                } else if (data[4] == 1) {
//                    mIRequestResponse.onDeleteDownloadedFirmWare(data[5]);
//                } else if (data[4] == 2) {
//                    mIRequestResponse.onUpdateFWStatusResponse(data[5]);
//                } else if (data[4] == 3) {
//                    mIRequestResponse.onFirmWareBlockResponse(data[5]);
//                }
//
//            }
//
//            if (tagStr.equals(ProtocolTag.DEVICE_INFO)) {                                   // 设备信息
//
//                mIDataResponse.onDeviceBaseInfo(data[4] + data[5] * 0x100, data[6], data[7], data[8], data[9], data[10], data[11]);
//
//            } else if (tagStr.equals(ProtocolTag.SUPPORT_LIST)) {                        // 支持列表
//
//                mIDataResponse.onDeviceSupportFunction(data[4], data[5], data[6], data[7], data[8], data[9], data[10]);
//
//            } else if (tagStr.equals(ProtocolTag.DEVICE_MAC)) {                           //  设备MAC
//
//                mIDataResponse.onDeviceMac(data[4], data[5], data[6], data[7], data[8], data[9]);
//
//            } else if (tagStr.equals(ProtocolTag.DEVICE_NAME)) {                        // 设备名称
//
//                byte[] nameBytes = new byte[data.length - 6];
//                for (int i = 0; i < nameBytes.length; i++) {
//                    nameBytes[i] = data[4 + i];
//                }
//                String name = new String(nameBytes);
//                mIDataResponse.onDeviceName(name);
//
//            } else if (tagStr.equals(ProtocolTag.CURRENT_HEART_RATE)) {     // 实时心率
//
//                mIDataResponse.onCurrentHR(data[4], Integer.valueOf(DataUtil.byteToHexString(new byte[]{data[5]}), 16));
//
//            } else if (tagStr.equals(ProtocolTag.CURRENT_BLOOD_PRESSURE)) {     // 当前血压
//
//                byte[] bpData = new byte[]{data[5], data[6]};
//                String bpDataStr = DataUtil.byteToHexString(bpData);
//                int systolic = Integer.valueOf(bpDataStr.substring(0, 2), 16);
//                int diastolic = Integer.valueOf(bpDataStr.substring(2, 4), 16);
//                mIDataResponse.onCurrentBP(data[4], systolic, diastolic);
//
//            } else if (tagStr.equals(ProtocolTag.FINDBAND)) {                  // 寻找手环
//
//                mIRequestResponse.onFindBandResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.HR_MEASUREMENT_ONOFF_CONTROL)) { // 心率测试开关控制
//
//                mIRequestResponse.onHRMeasurementOnoffControl(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.BP_MEASUREMENT_ONOFF_CONTROL)) {  // 血压测试开关控制
//
//                mIRequestResponse.onBPMeasurementOnoffControl(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.BLOOD_PRESSURE_CALIBRATION)) {            // 血压校准
//
//                mIRequestResponse.onBloodPressureCalibration(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.APP_EXIT)) {                                                              // App退出
//
//                mIRequestResponse.onAppExitResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.AEROBICS_COACH)) {                                            // 有氧教练开关控制
//
//                mIRequestResponse.onAerobicExerciseOnOffResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.BIND_DEVICE)) {                                                       // 绑定设备
//
//                mIRequestResponse.onBindDeviceResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.UNBIND_DEVICE)) {                                                 // 解除绑定
//
//                mIRequestResponse.onUnBindDeviceResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.MESSAGE_NOTIFICATION)) {                              // 信息提醒
//
//                mIRequestResponse.onMessageNotificationResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.DATA_POST_COMMAND_RESPONSE)) {     // 数据实时上传回应
//
//                mIRequestResponse.onRealTimeDataResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.SAMPLING_FREQ)) {                   // 采样频率
//
//                byte[] freqBytes = new byte[]{data[4], data[5]};
//                int freq = Integer.valueOf(DataUtil.byteToHexString(freqBytes), 16);
//                mIDataResponse.onQuerySamplingFreqResponse(freq);
//
//            } else if (tagStr.equals(ProtocolTag.WAVE_POST_COMMAND_RESPONSE)) {     // 波形上传响应
//
//                mIRequestResponse.onWaveFormPostResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.FIND_PHONE_RESPONSE)) {            // 寻找手机
//
//                mIRequestResponse.onFindPhoneResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.PREVENT_LOST_RESPONSE)) {      // 防丢失
//
//                mIRequestResponse.onPreventLostResponse(data[4]);
//
//            } else if (tagStr.equals(ProtocolTag.ANSWER_OR_REJECT_PHONE)) {  // 接听（拒接）电话
//
//                mIRequestResponse.onAnswerOrRejectPhoneResponse(data[4]);
//
//            }
//        }
//    }
//
//    /**
//     * 是否为错误类型
//     * 0xFB 到 0xFF
//     * @param bytes
//     * @return
//     */
//    public boolean isErrorType(byte[] bytes){
//        if( bytes[4] < 0){
//            return true;
//        }
//        return false;
//    }
//}
