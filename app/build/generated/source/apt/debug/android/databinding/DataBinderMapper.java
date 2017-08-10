
package android.databinding;
import com.rahulxyz.movieslook.BR;
class DataBinderMapper  {
    final static int TARGET_MIN_SDK = 23;
    public DataBinderMapper() {
    }
    public android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View view, int layoutId) {
        switch(layoutId) {
                case com.rahulxyz.movieslook.R.layout.description_secondary:
                    return com.rahulxyz.movieslook.databinding.DescriptionSecondaryBinding.bind(view, bindingComponent);
                case com.rahulxyz.movieslook.R.layout.description_primary:
                    return com.rahulxyz.movieslook.databinding.DescriptionPrimaryBinding.bind(view, bindingComponent);
                case com.rahulxyz.movieslook.R.layout.activity_detail:
                    return com.rahulxyz.movieslook.databinding.ActivityDetailBinding.bind(view, bindingComponent);
        }
        return null;
    }
    android.databinding.ViewDataBinding getDataBinder(android.databinding.DataBindingComponent bindingComponent, android.view.View[] views, int layoutId) {
        switch(layoutId) {
        }
        return null;
    }
    int getLayoutId(String tag) {
        if (tag == null) {
            return 0;
        }
        final int code = tag.hashCode();
        switch(code) {
            case -781348739: {
                if(tag.equals("layout/description_secondary_0")) {
                    return com.rahulxyz.movieslook.R.layout.description_secondary;
                }
                break;
            }
            case 726497163: {
                if(tag.equals("layout/description_primary_0")) {
                    return com.rahulxyz.movieslook.R.layout.description_primary;
                }
                break;
            }
            case 257710925: {
                if(tag.equals("layout/activity_detail_0")) {
                    return com.rahulxyz.movieslook.R.layout.activity_detail;
                }
                break;
            }
        }
        return 0;
    }
    String convertBrIdToString(int id) {
        if (id < 0 || id >= InnerBrLookup.sKeys.length) {
            return null;
        }
        return InnerBrLookup.sKeys[id];
    }
    private static class InnerBrLookup {
        static String[] sKeys = new String[]{
            "_all"};
    }
}