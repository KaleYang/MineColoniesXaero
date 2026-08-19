# MineColoniesXaero

一个用于连接 MineColonies 与 Xaero World Map 的 Minecraft NeoForge 客户端 Mod。

## 项目介绍

MineColoniesXaero 是一个纯客户端兼容 Mod。

目标是在 Xaero World Map 中显示 MineColonies 的 Colony 领地范围（Claim），让玩家可以直接在地图上查看自己的殖民地边界。

本 Mod 不需要服务器安装，不增加额外网络通信，也不会修改 MineColonies 原有的数据同步逻辑。

实现方式：

读取 MineColonies 已经同步到客户端的数据，将 Colony Claim 信息转换为 Xaero World Map Overlay 元素进行显示。

---

## 功能

当前已实现：

- ✅ 纯客户端 Mod
- ✅ 无需服务器安装
- ✅ 不需要额外 Packet 同步
- ✅ 读取 MineColonies 原生客户端同步数据
- ✅ Xaero World Map Overlay 显示 Colony Claim
- ✅ 鼠标悬停检测
- ✅ Colony 信息提示框


悬停信息目前显示：

- Colony 名称
- Colony ID
- 所在维度
- Colony 中心坐标
- Claim Chunk 数量


---

## 支持环境

Minecraft:

1.21.1

NeoForge:

21.1.233

Java:

17+


依赖：

- MineColonies 1.1.1294-1.21.1-snapshot
- Xaero World Map 1.44.2


---

## 技术实现

整体结构：

MineColonies 客户端同步数据

      ↓
      
ClientColonyCache

      ↓
      
ClaimElementProvider

      ↓
      
ClaimMapElement

      ↓
      
Xaero World Map Overlay

      ↓
      
ClaimElementRenderer


设计原则：

- 不修改 MineColonies 数据读取方式
- 不添加服务器通信
- 不影响原有 MineColonies 工作逻辑
- 使用 Xaero 官方扩展接口实现地图显示


---

## 开发过程说明

本项目采用 AI 辅助开发方式。

AI 在开发过程中主要用于：

- API 查询与分析
- 反编译代码辅助分析
- 调试思路提供
- 代码结构建议
- 问题定位辅助


项目中的：

- 架构设计
- 技术方案选择
- 调试验证
- 功能测试
- 最终代码修改

均由开发者人工确认和实现。


AI 作为编程辅助工具参与开发。

---

## 当前开发状态

当前版本属于可能持续开发阶段。

已完成：

- MineColonies 客户端数据读取
- Colony 数据缓存
- Xaero Overlay 注册
- Claim 区域绘制
- Hover 生命周期适配
- 信息提示框显示


计划功能：

- 更完善的 Tooltip UI
- Colony 自定义颜色
- 多 Colony 显示优化
- 更多 Xaero 地图交互功能
- Colony 信息扩展展示


---

## 为什么开发这个 Mod

MineColonies 本身拥有完整的殖民地系统，但是默认情况下玩家无法直接在 Xaero World Map 中查看殖民地范围。

本项目希望提供一个轻量的客户端解决方案：

打开地图 → 查看殖民地范围 → 快速规划建设区域。


---

## 开源说明

欢迎：

- 提交 Issue
- 提交建议
- 参与代码改进


由于项目仍处于开发阶段，API 和功能可能会持续调整。
