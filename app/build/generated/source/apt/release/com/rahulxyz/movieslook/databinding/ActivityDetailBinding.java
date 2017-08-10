package com.rahulxyz.movieslook.databinding;
import com.rahulxyz.movieslook.R;
import com.rahulxyz.movieslook.BR;
import android.view.View;
public class ActivityDetailBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = new android.databinding.ViewDataBinding.IncludedLayouts(5);
        sIncludes.setIncludes(1, 
            new String[] {"description_primary", "description_secondary"},
            new int[] {2, 3},
            new int[] {R.layout.description_primary, R.layout.description_secondary});
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.progressBar, 4);
    }
    // views
    public final android.widget.ScrollView detailScroll;
    private final android.widget.LinearLayout mboundView1;
    public final com.rahulxyz.movieslook.databinding.DescriptionPrimaryBinding primary;
    public final android.widget.ProgressBar progressBar;
    public final com.rahulxyz.movieslook.databinding.DescriptionSecondaryBinding secondary;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public ActivityDetailBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 2);
        final Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.detailScroll = (android.widget.ScrollView) bindings[0];
        this.detailScroll.setTag(null);
        this.mboundView1 = (android.widget.LinearLayout) bindings[1];
        this.mboundView1.setTag(null);
        this.primary = (com.rahulxyz.movieslook.databinding.DescriptionPrimaryBinding) bindings[2];
        setContainedBinding(this.primary);
        this.progressBar = (android.widget.ProgressBar) bindings[4];
        this.secondary = (com.rahulxyz.movieslook.databinding.DescriptionSecondaryBinding) bindings[3];
        setContainedBinding(this.secondary);
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x4L;
        }
        primary.invalidateAll();
        secondary.invalidateAll();
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
        }
        if (primary.hasPendingBindings()) {
            return true;
        }
        if (secondary.hasPendingBindings()) {
            return true;
        }
        return false;
    }

    public boolean setVariable(int variableId, Object variable) {
        switch(variableId) {
        }
        return false;
    }

    @Override
    protected boolean onFieldChange(int localFieldId, Object object, int fieldId) {
        switch (localFieldId) {
            case 0 :
                return onChangeSecondary((com.rahulxyz.movieslook.databinding.DescriptionSecondaryBinding) object, fieldId);
            case 1 :
                return onChangePrimary((com.rahulxyz.movieslook.databinding.DescriptionPrimaryBinding) object, fieldId);
        }
        return false;
    }
    private boolean onChangeSecondary(com.rahulxyz.movieslook.databinding.DescriptionSecondaryBinding Secondary, int fieldId) {
        switch (fieldId) {
            case BR._all: {
                synchronized(this) {
                        mDirtyFlags |= 0x1L;
                }
                return true;
            }
        }
        return false;
    }
    private boolean onChangePrimary(com.rahulxyz.movieslook.databinding.DescriptionPrimaryBinding Primary, int fieldId) {
        switch (fieldId) {
            case BR._all: {
                synchronized(this) {
                        mDirtyFlags |= 0x2L;
                }
                return true;
            }
        }
        return false;
    }

    @Override
    protected void executeBindings() {
        long dirtyFlags = 0;
        synchronized(this) {
            dirtyFlags = mDirtyFlags;
            mDirtyFlags = 0;
        }
        // batch finished
        executeBindingsOn(primary);
        executeBindingsOn(secondary);
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static ActivityDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityDetailBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<ActivityDetailBinding>inflate(inflater, com.rahulxyz.movieslook.R.layout.activity_detail, root, attachToRoot, bindingComponent);
    }
    public static ActivityDetailBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityDetailBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.rahulxyz.movieslook.R.layout.activity_detail, null, false), bindingComponent);
    }
    public static ActivityDetailBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static ActivityDetailBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/activity_detail_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new ActivityDetailBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): secondary
        flag 1 (0x2L): primary
        flag 2 (0x3L): null
    flag mapping end*/
    //end
}