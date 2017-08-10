package com.rahulxyz.movieslook.databinding;
import com.rahulxyz.movieslook.R;
import com.rahulxyz.movieslook.BR;
import android.view.View;
public class DescriptionSecondaryBinding extends android.databinding.ViewDataBinding  {

    private static final android.databinding.ViewDataBinding.IncludedLayouts sIncludes;
    private static final android.util.SparseIntArray sViewsWithIds;
    static {
        sIncludes = null;
        sViewsWithIds = new android.util.SparseIntArray();
        sViewsWithIds.put(R.id.title, 1);
        sViewsWithIds.put(R.id.plot, 2);
        sViewsWithIds.put(R.id.review, 3);
        sViewsWithIds.put(R.id.trailers_label, 4);
        sViewsWithIds.put(R.id.trailerRecycler, 5);
        sViewsWithIds.put(R.id.review_label, 6);
        sViewsWithIds.put(R.id.reviewRecycler, 7);
    }
    // views
    private final android.support.constraint.ConstraintLayout mboundView0;
    public final android.widget.TextView plot;
    public final android.widget.TextView review;
    public final android.widget.TextView reviewLabel;
    public final android.support.v7.widget.RecyclerView reviewRecycler;
    public final android.widget.TextView title;
    public final android.support.v7.widget.RecyclerView trailerRecycler;
    public final android.widget.TextView trailersLabel;
    // variables
    // values
    // listeners
    // Inverse Binding Event Handlers

    public DescriptionSecondaryBinding(android.databinding.DataBindingComponent bindingComponent, View root) {
        super(bindingComponent, root, 0);
        final Object[] bindings = mapBindings(bindingComponent, root, 8, sIncludes, sViewsWithIds);
        this.mboundView0 = (android.support.constraint.ConstraintLayout) bindings[0];
        this.mboundView0.setTag(null);
        this.plot = (android.widget.TextView) bindings[2];
        this.review = (android.widget.TextView) bindings[3];
        this.reviewLabel = (android.widget.TextView) bindings[6];
        this.reviewRecycler = (android.support.v7.widget.RecyclerView) bindings[7];
        this.title = (android.widget.TextView) bindings[1];
        this.trailerRecycler = (android.support.v7.widget.RecyclerView) bindings[5];
        this.trailersLabel = (android.widget.TextView) bindings[4];
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

    public static DescriptionSecondaryBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot) {
        return inflate(inflater, root, attachToRoot, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DescriptionSecondaryBinding inflate(android.view.LayoutInflater inflater, android.view.ViewGroup root, boolean attachToRoot, android.databinding.DataBindingComponent bindingComponent) {
        return android.databinding.DataBindingUtil.<DescriptionSecondaryBinding>inflate(inflater, com.rahulxyz.movieslook.R.layout.description_secondary, root, attachToRoot, bindingComponent);
    }
    public static DescriptionSecondaryBinding inflate(android.view.LayoutInflater inflater) {
        return inflate(inflater, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DescriptionSecondaryBinding inflate(android.view.LayoutInflater inflater, android.databinding.DataBindingComponent bindingComponent) {
        return bind(inflater.inflate(com.rahulxyz.movieslook.R.layout.description_secondary, null, false), bindingComponent);
    }
    public static DescriptionSecondaryBinding bind(android.view.View view) {
        return bind(view, android.databinding.DataBindingUtil.getDefaultComponent());
    }
    public static DescriptionSecondaryBinding bind(android.view.View view, android.databinding.DataBindingComponent bindingComponent) {
        if (!"layout/description_secondary_0".equals(view.getTag())) {
            throw new RuntimeException("view tag isn't correct on view:" + view.getTag());
        }
        return new DescriptionSecondaryBinding(bindingComponent, view);
    }
    /* flag mapping
        flag 0 (0x1L): null
    flag mapping end*/
    //end
}