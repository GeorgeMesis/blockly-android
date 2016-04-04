/*
 *  Copyright 2016 Google Inc. All Rights Reserved.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.google.blockly.android.ui.fieldview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.google.blockly.model.Field;

/**
 * Renders a checkbox as part of a BlockView.
 */
public class BasicFieldCheckboxView extends CheckBox implements FieldView {
    protected final Field.FieldCheckbox.Observer mFieldObserver
            = new Field.FieldCheckbox.Observer() {
        @Override
        public void onCheckChanged(Field.FieldCheckbox field, boolean newState) {
            if (isChecked() != newState) {
                setChecked(newState);
            }
        }
    };

    protected Field.FieldCheckbox mCheckboxField = null;

    public BasicFieldCheckboxView(Context context) {
        super(context);
        initOnCheckedChangeListener();
    }

    public BasicFieldCheckboxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initOnCheckedChangeListener();
    }

    public BasicFieldCheckboxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initOnCheckedChangeListener();
    }

    private void initOnCheckedChangeListener() {
        setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCheckboxField != null) {
                    mCheckboxField.setChecked(isChecked);
                }
            }
        });
    }

    /**
     * Sets the {@link Field} model for this view, if not null. Otherwise, disconnects the prior
     * field model.
     *
     * @param checkboxField The checkbox field to view.
     */
    public void setField(Field.FieldCheckbox checkboxField) {
        if (mCheckboxField == checkboxField) {
            return;
        }

        if (mCheckboxField != null) {
            mCheckboxField.unregisterObserver(mFieldObserver);
        }
        mCheckboxField = checkboxField;
        if (mCheckboxField != null) {
            setChecked(mCheckboxField.isChecked());
            mCheckboxField.registerObserver(mFieldObserver);
        }
    }

    @Override
    public Field getField() {
        return mCheckboxField;
    }

    @Override
    public void unlinkField() {
        setField(null);
    }
}