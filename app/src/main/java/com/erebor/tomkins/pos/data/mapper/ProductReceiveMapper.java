package com.erebor.tomkins.pos.data.mapper;

import com.erebor.tomkins.pos.data.local.model.TrxTerimaDBModel;
import com.erebor.tomkins.pos.data.local.model.TrxTerimaDetDBModel;
import com.erebor.tomkins.pos.data.ui.ProductReceiveUiModel;
import com.erebor.tomkins.pos.helper.DateConverterHelper;

import java.util.ArrayList;
import java.util.List;

public class ProductReceiveMapper {
    public static List<ProductReceiveUiModel> toProductReceive(List<TrxTerimaDBModel> trxTerimaDBModels,
                                                               DateConverterHelper dateConverterHelper) {
        List<ProductReceiveUiModel> result = new ArrayList<>();
        if (trxTerimaDBModels == null) {
            return result;
        }
        for (TrxTerimaDBModel trxTerimaDBModel : trxTerimaDBModels) {
            double qtyTotal = 0;
            double qtyTerima = 0;
            for (TrxTerimaDetDBModel trxTerimaDetDBModel : trxTerimaDBModel.getListDetail()) {
                qtyTotal += trxTerimaDetDBModel.getQtyDO();
                qtyTerima += trxTerimaDetDBModel.getQtyTerima();
            }
            ProductReceiveUiModel productReceiveUiModel = new ProductReceiveUiModel(
                    trxTerimaDBModel.getNoDO(),
                    dateConverterHelper.getDifference(trxTerimaDBModel.getTglKirimGBJ() == null ?
                            0 : trxTerimaDBModel.getTglKirimGBJ().getTime()),
                    dateConverterHelper.getDifference(trxTerimaDBModel.getTglTerimaCnt() == null ?
                            0 : trxTerimaDBModel.getTglTerimaCnt().getTime()),
                    trxTerimaDBModel.getCatatan(),
                    trxTerimaDBModel.getStatusDO() == 1,
                    qtyTotal,
                    qtyTerima);
            result.add(productReceiveUiModel);
        }
        return result;
    }
}
