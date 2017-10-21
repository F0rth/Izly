package android.support.design.internal;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.R;
import android.support.v7.internal.view.menu.MenuBuilder;
import android.support.v7.internal.view.menu.MenuItemImpl;
import android.support.v7.internal.view.menu.MenuPresenter;
import android.support.v7.internal.view.menu.MenuPresenter.Callback;
import android.support.v7.internal.view.menu.MenuView;
import android.support.v7.internal.view.menu.SubMenuBuilder;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;

public class NavigationMenuPresenter implements MenuPresenter, OnItemClickListener {
    private static final String STATE_HIERARCHY = "android:menu:list";
    private NavigationMenuAdapter mAdapter;
    private Callback mCallback;
    private LinearLayout mHeader;
    private ColorStateList mIconTintList;
    private int mId;
    private Drawable mItemBackground;
    private LayoutInflater mLayoutInflater;
    private MenuBuilder mMenu;
    private NavigationMenuView mMenuView;
    private int mPaddingSeparator;
    private int mPaddingTopDefault;
    private ColorStateList mTextColor;

    class NavigationMenuAdapter extends BaseAdapter {
        private static final int VIEW_TYPE_NORMAL = 0;
        private static final int VIEW_TYPE_SEPARATOR = 2;
        private static final int VIEW_TYPE_SUBHEADER = 1;
        private final ArrayList<NavigationMenuItem> mItems = new ArrayList();
        private ColorDrawable mTransparentIcon;

        NavigationMenuAdapter() {
            prepareMenuItems();
        }

        private void appendTransparentIconIfMissing(int i, int i2) {
            while (i < i2) {
                MenuItem menuItem = ((NavigationMenuItem) this.mItems.get(i)).getMenuItem();
                if (menuItem.getIcon() == null) {
                    if (this.mTransparentIcon == null) {
                        this.mTransparentIcon = new ColorDrawable(17170445);
                    }
                    menuItem.setIcon(this.mTransparentIcon);
                }
                i++;
            }
        }

        private void prepareMenuItems() {
            this.mItems.clear();
            int i = -1;
            int size = NavigationMenuPresenter.this.mMenu.getVisibleItems().size();
            int i2 = 0;
            int i3 = 0;
            int i4 = 0;
            while (i2 < size) {
                int i5;
                int i6;
                MenuItemImpl menuItemImpl = (MenuItemImpl) NavigationMenuPresenter.this.mMenu.getVisibleItems().get(i2);
                if (menuItemImpl.hasSubMenu()) {
                    SubMenu subMenu = menuItemImpl.getSubMenu();
                    if (subMenu.hasVisibleItems()) {
                        if (i2 != 0) {
                            this.mItems.add(NavigationMenuItem.separator(NavigationMenuPresenter.this.mPaddingSeparator, 0));
                        }
                        this.mItems.add(NavigationMenuItem.of(menuItemImpl));
                        int size2 = this.mItems.size();
                        int size3 = subMenu.size();
                        i5 = 0;
                        for (int i7 = 0; i7 < size3; i7++) {
                            MenuItem item = subMenu.getItem(i7);
                            if (item.isVisible()) {
                                if (i5 == 0 && item.getIcon() != null) {
                                    i5 = 1;
                                }
                                this.mItems.add(NavigationMenuItem.of((MenuItemImpl) item));
                            }
                        }
                        if (i5 != 0) {
                            appendTransparentIconIfMissing(size2, this.mItems.size());
                        }
                    }
                    i6 = i;
                    i5 = i4;
                    i = i3;
                } else {
                    i5 = menuItemImpl.getGroupId();
                    if (i5 != i) {
                        i4 = this.mItems.size();
                        i3 = menuItemImpl.getIcon() != null ? 1 : 0;
                        if (i2 != 0) {
                            this.mItems.add(NavigationMenuItem.separator(NavigationMenuPresenter.this.mPaddingSeparator, NavigationMenuPresenter.this.mPaddingSeparator));
                            i = i4 + 1;
                            i4 = i3;
                        }
                        i = i4;
                        i4 = i3;
                    } else {
                        if (i3 == 0 && menuItemImpl.getIcon() != null) {
                            appendTransparentIconIfMissing(i4, this.mItems.size());
                            i = i4;
                            i4 = 1;
                        }
                        i = i4;
                        i4 = i3;
                    }
                    if (i4 != 0 && menuItemImpl.getIcon() == null) {
                        menuItemImpl.setIcon(17170445);
                    }
                    this.mItems.add(NavigationMenuItem.of(menuItemImpl));
                    i6 = i5;
                    i5 = i;
                    i = i4;
                }
                i2++;
                i3 = i;
                i = i6;
                i4 = i5;
            }
        }

        public boolean areAllItemsEnabled() {
            return false;
        }

        public int getCount() {
            return this.mItems.size();
        }

        public NavigationMenuItem getItem(int i) {
            return (NavigationMenuItem) this.mItems.get(i);
        }

        public long getItemId(int i) {
            return (long) i;
        }

        public int getItemViewType(int i) {
            NavigationMenuItem item = getItem(i);
            return item.isSeparator() ? 2 : item.getMenuItem().hasSubMenu() ? 1 : 0;
        }

        public View getView(int i, View view, ViewGroup viewGroup) {
            NavigationMenuItem item = getItem(i);
            View inflate;
            switch (getItemViewType(i)) {
                case 0:
                    inflate = view == null ? NavigationMenuPresenter.this.mLayoutInflater.inflate(R.layout.design_navigation_item, viewGroup, false) : view;
                    NavigationMenuItemView navigationMenuItemView = (NavigationMenuItemView) inflate;
                    navigationMenuItemView.setIconTintList(NavigationMenuPresenter.this.mIconTintList);
                    navigationMenuItemView.setTextColor(NavigationMenuPresenter.this.mTextColor);
                    navigationMenuItemView.setBackgroundDrawable(NavigationMenuPresenter.this.mItemBackground);
                    navigationMenuItemView.initialize(item.getMenuItem(), 0);
                    return inflate;
                case 1:
                    inflate = view == null ? NavigationMenuPresenter.this.mLayoutInflater.inflate(R.layout.design_navigation_item_subheader, viewGroup, false) : view;
                    ((TextView) inflate).setText(item.getMenuItem().getTitle());
                    return inflate;
                case 2:
                    if (view == null) {
                        view = NavigationMenuPresenter.this.mLayoutInflater.inflate(R.layout.design_navigation_item_separator, viewGroup, false);
                    }
                    view.setPadding(0, item.getPaddingTop(), 0, item.getPaddingBottom());
                    break;
            }
            return view;
        }

        public int getViewTypeCount() {
            return 3;
        }

        public boolean isEnabled(int i) {
            return getItem(i).isEnabled();
        }

        public void notifyDataSetChanged() {
            prepareMenuItems();
            super.notifyDataSetChanged();
        }
    }

    static class NavigationMenuItem {
        private final MenuItemImpl mMenuItem;
        private final int mPaddingBottom;
        private final int mPaddingTop;

        private NavigationMenuItem(MenuItemImpl menuItemImpl, int i, int i2) {
            this.mMenuItem = menuItemImpl;
            this.mPaddingTop = i;
            this.mPaddingBottom = i2;
        }

        public static NavigationMenuItem of(MenuItemImpl menuItemImpl) {
            return new NavigationMenuItem(menuItemImpl, 0, 0);
        }

        public static NavigationMenuItem separator(int i, int i2) {
            return new NavigationMenuItem(null, i, i2);
        }

        public MenuItemImpl getMenuItem() {
            return this.mMenuItem;
        }

        public int getPaddingBottom() {
            return this.mPaddingBottom;
        }

        public int getPaddingTop() {
            return this.mPaddingTop;
        }

        public boolean isEnabled() {
            return (this.mMenuItem == null || this.mMenuItem.hasSubMenu() || !this.mMenuItem.isEnabled()) ? false : true;
        }

        public boolean isSeparator() {
            return this.mMenuItem == null;
        }
    }

    public void addHeaderView(@NonNull View view) {
        this.mHeader.addView(view);
        this.mMenuView.setPadding(0, 0, 0, this.mMenuView.getPaddingBottom());
    }

    public boolean collapseItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public boolean expandItemActionView(MenuBuilder menuBuilder, MenuItemImpl menuItemImpl) {
        return false;
    }

    public boolean flagActionItems() {
        return false;
    }

    public int getId() {
        return this.mId;
    }

    public Drawable getItemBackground() {
        return this.mItemBackground;
    }

    @Nullable
    public ColorStateList getItemTextColor() {
        return this.mTextColor;
    }

    @Nullable
    public ColorStateList getItemTintList() {
        return this.mIconTintList;
    }

    public MenuView getMenuView(ViewGroup viewGroup) {
        if (this.mMenuView == null) {
            this.mMenuView = (NavigationMenuView) this.mLayoutInflater.inflate(R.layout.design_navigation_menu, viewGroup, false);
            if (this.mAdapter == null) {
                this.mAdapter = new NavigationMenuAdapter();
            }
            this.mHeader = (LinearLayout) this.mLayoutInflater.inflate(R.layout.design_navigation_item_header, this.mMenuView, false);
            this.mMenuView.addHeaderView(this.mHeader);
            this.mMenuView.setAdapter(this.mAdapter);
            this.mMenuView.setOnItemClickListener(this);
        }
        return this.mMenuView;
    }

    public View inflateHeaderView(@LayoutRes int i) {
        View inflate = this.mLayoutInflater.inflate(i, this.mHeader, false);
        addHeaderView(inflate);
        return inflate;
    }

    public void initForMenu(Context context, MenuBuilder menuBuilder) {
        this.mLayoutInflater = LayoutInflater.from(context);
        this.mMenu = menuBuilder;
        Resources resources = context.getResources();
        this.mPaddingTopDefault = resources.getDimensionPixelOffset(R.dimen.navigation_padding_top_default);
        this.mPaddingSeparator = resources.getDimensionPixelOffset(R.dimen.navigation_separator_vertical_padding);
    }

    public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
        if (this.mCallback != null) {
            this.mCallback.onCloseMenu(menuBuilder, z);
        }
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int i, long j) {
        int headerViewsCount = i - this.mMenuView.getHeaderViewsCount();
        if (headerViewsCount >= 0) {
            this.mMenu.performItemAction(this.mAdapter.getItem(headerViewsCount).getMenuItem(), this, 0);
        }
    }

    public void onRestoreInstanceState(Parcelable parcelable) {
        SparseArray sparseParcelableArray = ((Bundle) parcelable).getSparseParcelableArray("android:menu:list");
        if (sparseParcelableArray != null) {
            this.mMenuView.restoreHierarchyState(sparseParcelableArray);
        }
    }

    public Parcelable onSaveInstanceState() {
        Parcelable bundle = new Bundle();
        SparseArray sparseArray = new SparseArray();
        if (this.mMenuView != null) {
            this.mMenuView.saveHierarchyState(sparseArray);
        }
        bundle.putSparseParcelableArray("android:menu:list", sparseArray);
        return bundle;
    }

    public boolean onSubMenuSelected(SubMenuBuilder subMenuBuilder) {
        return false;
    }

    public void removeHeaderView(@NonNull View view) {
        this.mHeader.removeView(view);
        if (this.mHeader.getChildCount() == 0) {
            this.mMenuView.setPadding(0, this.mPaddingTopDefault, 0, this.mMenuView.getPaddingBottom());
        }
    }

    public void setCallback(Callback callback) {
        this.mCallback = callback;
    }

    public void setId(int i) {
        this.mId = i;
    }

    public void setItemBackground(Drawable drawable) {
        this.mItemBackground = drawable;
    }

    public void setItemIconTintList(@Nullable ColorStateList colorStateList) {
        this.mIconTintList = colorStateList;
    }

    public void setItemTextColor(@Nullable ColorStateList colorStateList) {
        this.mTextColor = colorStateList;
    }

    public void updateMenuView(boolean z) {
        if (this.mAdapter != null) {
            this.mAdapter.notifyDataSetChanged();
        }
    }
}
