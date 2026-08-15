# SAFETYAI API 摘要

版权所有 © 2026 上海如静知华信息科技有限公司。

| 方法 | 路径 | 说明 |
| --- | --- | --- |
| POST | `/api/auth/login` | 登录并获取 JWT |
| GET | `/api/admin/dashboard` | 知华作业安全 AI 平台运营控制台 |
| GET | `/api/admin/work-orders` | 危险作业评估任务列表 |
| GET | `/api/shopfloor/dashboard` | 安全专员核查工作台 |
| POST | `/api/shopfloor/work-orders/{id}/reports` | 提交现场核查反馈 |
| POST | `/api/ai/safety/assess` | 作业风险、放行决策和控制措施 |
| POST | `/api/shopfloor/ai-risk-assessment` | AI 功能上线风险初筛 |

除登录外均需 `Authorization: Bearer <token>`。社区演示实现不调用外部模型，不需要 API Key。
