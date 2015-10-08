/*
 * Copyright (C) 2015 Two Toasters
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
package com.yieryi.gladtohear.tools.recycleviewanimation.effects;

import android.view.View;
import android.view.ViewPropertyAnimator;

import com.yieryi.gladtohear.tools.recycleviewanimation.JazzyEffect;
import com.yieryi.gladtohear.tools.recycleviewanimation.JazzyHelper;

public class FadeEffect implements JazzyEffect {

    private static final int DURATION_MULTIPLIER = 5;

    @Override
    public void initView(View item, int position, int scrollDirection) {
        item.setAlpha(JazzyHelper.TRANSPARENT);
    }

    @Override
    public void setupAnimation(View item, int position, int scrollDirection, ViewPropertyAnimator animator) {
        animator.setDuration(JazzyHelper.DURATION * DURATION_MULTIPLIER)
                .alpha(JazzyHelper.OPAQUE);
    }
}
