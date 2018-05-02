package me.aidoc.client.entity.resp;

import me.aidoc.client.entity.BaseResp;

import xiaofei.library.datastorage.annotation.ClassId;
import xiaofei.library.datastorage.annotation.ObjectId;

@ClassId("FormulaResp")
public class FormulaResp extends BaseResp {
    @ObjectId
    private String formula;

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}
