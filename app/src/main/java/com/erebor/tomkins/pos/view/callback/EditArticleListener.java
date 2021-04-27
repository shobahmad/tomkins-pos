package com.erebor.tomkins.pos.view.callback;

import com.erebor.tomkins.pos.data.ui.ArticleUiModel;

public interface EditArticleListener {
    void onEditGrade(ArticleUiModel articleUiModel, String grade);
}
