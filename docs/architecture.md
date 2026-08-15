# SAFETYAI 架构

版权所有 © 2026 上海如静知华信息科技有限公司。

Vue 3 管理端和 H5 工作台通过 JWT 调用 Spring Boot REST API。领域服务 `SafetyRiskService` 组合作业危害、高风险工序、人员规模、天气、培训与防护合规数据，输出风险等级、放行决策与必需控制措施；JPA 与 Flyway 管理 MySQL 数据，Docker Compose 负责本地编排。

生产落地时应接入企业 SSO、电子作业票、人员资质、气体检测、视频和告警平台。AI 结果只作辅助，危险作业必须由有权限的安全责任人批准。
