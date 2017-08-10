package com.rahulxyz.movieslook.databinding;
import com.rahulxyz.movieslook.R;
import com.rahulxyz.movieslook.BR;
import android.view.View;
public class DescriptionPrimaryBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.poster, 1);
        sViewsWithIds.put(R.id.releaseDate, 2);
        sViewsWithIds.put(R.id.rating, 3);
        sViewsWithIds.put(R.id.star, 4);
    }
    // views
    private final android.support.constraint.ConstraintLayout mboundView0;
    public final android.widget.ImageView poster;
    public final android.widget.TextView rating;
    public final android.widget.TextView releaseDate;
    public final android.widget.ImageView star;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DescriptionPrimaryBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 5, sIncludes, sViewsWithIds);
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.poster = (android.widget.ImageView) bindings[1];
        this.rating = (android.widget.TextView) bindings[3];
        this.releaseDate = (android.widget.TextView) bindings[2];
        this.star = (android.widget.ImageView) bindings[4];
        setRootTag(root);
        // listeners
        invalidateAll();
    }

    @Override
    public void invalidateAll() {
        synchronized(this) {
                mDirtyFlags = 0x1L;
        }
        requestRebind();
    }

    @Override
    public boolean hasPendingBindings() {
        synchronized(this) {
            if (mDirtyFlags != 0) {
                return true;
            }
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
    }
    // Listener Stub Implementations
    // callback impls
    // dirty flag
    private  long mDirtyFlags = 0xffffffffffffffffL;

    public static DescriptionPrimaryBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DescriptionPrimaryBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<DescriptionPrimaryBinding>inflate(inflater, com.rahulxyz.movieslook.R.layout.description_primary, root, attachToRoot, bindingComponent);
    }
    public static DescriptionPrimaryBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DescriptionPrimaryBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.rahulxyz.movieslook.R.layout.description_primary, null, false), bindingComponent);
    }
    public static DescriptionPrimaryBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DescriptionPrimaryBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/description_primary_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new DescriptionPrimaryBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}