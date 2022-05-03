# Universe

红岩中期考核--------Universe

三天写完这个app----现在人都还是麻的

## 软件介绍

此软件是用于记录专注一件事的时长，以及记录要做的事情。

### 1.使用上

<img src="https://github.com/RQ527/Test3/blob/master/p1.jpg" width="200" height="400" />

单击图中圆环即可弹出选择按钮，若没有数据可创建。

<img src=https://github.com/RQ527/Test3/blob/master/p2.jpg width="200" height="400" />

选择完要专注的事件之后即可开始计时。

长按圆环即可拖动圆点，随着圆点拖动，倒计时数值也会变动，最大为0，最小为120分钟，以秒为单位进行计时。

开始过后不可点击，并且一直记录专注时长。倒计时为0会震动并通知消息在状态栏。

再次长按可暂停并调整时长，拖动至0，即可停止。

中间页面可查看未点亮的星球和已经完成的星球的信息。可进行对未点亮的星球进行删除和修改，已点亮的星球不可更改。

### 2.技术上

使用mvvm架构，kotlin+coroutines+room+livedata+databinding

时间有些急使用并不是那么正确。

### 3.遇到的难题

ringView的自定义，长按事件与点击事件的处理

权限的处理

popupWindow的弹出控制

### 4.一些使用示例


<img src=https://github.com/RQ527/Test3/blob/master/g1.gif height="400" />  
<img src=https://github.com/RQ527/Test3/blob/master/g2.gif height="400" />
<img src=https://github.com/RQ527/Test3/blob/master/g3.gif height="400" />


