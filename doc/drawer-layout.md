# DrawerLayoutメモ

DrawerLayoutの開閉をするアイコンを用意しない場合は、以下はこんな感じ
```
var toggle = ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.item, R.string.amount).apply {
    // デフォルトはtrueだけど、わかりやすいように書いておいた
    isDrawerIndicatorEnabled = true
}
drawerLayout.addDrawerListener(apply)
toggle.syncState()
```