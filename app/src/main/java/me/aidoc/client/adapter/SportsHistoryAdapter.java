package me.aidoc.client.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import me.aidoc.client.entity.resp.StepListResp;
import me.aidoc.client.util.StepUtil;


public class SportsHistoryAdapter extends BaseAdapter {

    private List<StepListResp.Data> mDataSet;
    private Context context;

    public SportsHistoryAdapter(Context context, List<StepListResp.Data> dataSet) {
        this.mDataSet = dataSet;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = View.inflate(context, me.aidoc.client.R.layout.item_history, null);

        TextView tvDate =  convertView.findViewById(me.aidoc.client.R.id.tvDate);
        tvDate.setText(mDataSet.get(position).getYearMonth());

        TextView tvAidoc =  convertView.findViewById(me.aidoc.client.R.id.tvAidoc);
        tvAidoc.setText(""+mDataSet.get(position).getAidoc());

        TextView tvStep =  convertView.findViewById(me.aidoc.client.R.id.tvStep);
        int steps = mDataSet.get(position).getSteps();
        tvStep.setText(""+mDataSet.get(position).getSteps());

        // 根据 getSteps计算热量和距离
        TextView tvEnergy =  convertView.findViewById(me.aidoc.client.R.id.tvEnergy);
        tvEnergy.setText(StepUtil.getEnengy(context,steps));

        TextView tvDistance =  convertView.findViewById(me.aidoc.client.R.id.tvDistance);
        tvDistance.setText(StepUtil.getDistance(steps));

        return convertView;
    }

/*    private String getEnengy(int steps){
        FormulaResp formulaResp = DataUtil.getInstance(context).load(FormulaResp.class, MyConstants.FORMULA);
        String formula="weight*step*0.5*1.036";
        if (formulaResp!=null){
            formula=formulaResp.getFormula();
        }
        // 开始计算
        ScriptEngine jse = new ScriptEngineManager().getEngineByName("rhino");
        String evalStr = formula.replaceAll("weight", "60").replaceAll("step", "" + steps);
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
    }*/


}
