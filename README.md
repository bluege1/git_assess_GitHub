# 系统运行能力考核验证

最小可运行前后端分离框架：Vue3 + Vite + TypeScript ↔ Java 17 + Spring Boot（无数据库）。

## 项目结构

```
project_git_assess/
├── frontend/          # Vue3 + Vite + TypeScript
├── backend/           # Spring Boot + Maven Wrapper
└── README.md
```

## 环境要求

| 组件 | 要求 |
|------|------|
| 前端 | Node.js 18+、npm |
| 后端 | JDK 17+（需配置 `JAVA_HOME`） |
| Maven | **不需要**（使用 `mvnw` / `mvnw.cmd`） |

## 启动步骤

### 1. 启动后端

```powershell
cd backend

# Windows：确保 JAVA_HOME 指向 JDK 根目录，例如：
# $env:JAVA_HOME = "D:\Java 21"

mvnw.cmd spring-boot:run
```

Linux / macOS：

```bash
cd backend
export JAVA_HOME=/path/to/jdk-17
./mvnw spring-boot:run
```

启动成功后控制台出现：`Started AssessApplication`，服务监听 `http://localhost:8080`。

### 2. 启动前端

新开一个终端：

```powershell
cd frontend
npm install
npm run dev
```

浏览器访问：`http://localhost:5173`

## 联调验证

### 方式一：页面验证（推荐）

1. 先启动后端，再启动前端
2. 打开 `http://localhost:5173`
3. 页面自动请求 `/api/health`
4. 显示绿色 **「联调成功」** 及 `status` / `message` / `service` 即通过

### 方式二：直接调后端 API

```powershell
Invoke-RestMethod http://localhost:8080/api/health
```

期望返回：

```json
{
  "status": "ok",
  "message": "后端服务运行正常",
  "service": "assess-backend"
}
```

### 方式三：经 Vite 代理验证

```powershell
Invoke-RestMethod http://localhost:5173/api/health
```

前端 `vite.config.ts` 已将 `/api` 代理到 `http://localhost:8080`，开发时无 CORS 问题。后端 `CorsConfig` 也已放行 `localhost` 来源，支持直连后端调试。

## API 说明

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/health` | 健康检查，用于前后端联调 |

## Maven Wrapper 说明

`backend` 已包含完整 Maven Wrapper，可在**未安装 Maven** 的机器上构建/运行：

- `mvnw` / `mvnw.cmd` — 启动脚本
- `.mvn/wrapper/maven-wrapper.properties` — Maven 版本与下载地址
- `.mvn/wrapper/maven-wrapper.jar` — Wrapper 引导 JAR（已提交，离线可用）

### 如何重新生成 Wrapper（可选）

在已安装 Maven 的机器上，于 `backend` 目录执行：

```bash
mvn -N wrapper:wrapper -Dtype=bin "-Dmaven=3.9.11"
```

然后下载 JAR 到仓库（若未自动生成）：

```powershell
Invoke-WebRequest `
  -Uri "https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.3.4/maven-wrapper-3.3.4.jar" `
  -OutFile ".mvn/wrapper/maven-wrapper.jar"
```

首次运行 `mvnw` 会自动从 Maven Central 下载 Apache Maven 3.9.11 到用户目录 `~/.m2/wrapper/dists/`。

## 常见问题

**`JAVA_HOME not found`**

设置环境变量指向 JDK 17+ 安装目录（含 `bin/java.exe`），例如：

```powershell
$env:JAVA_HOME = "D:\Java 21"
```

**前端显示「联调失败」**

确认后端已启动且 `http://localhost:8080/api/health` 可访问。

**端口占用**

- 后端端口：`backend/src/main/resources/application.properties` 中 `server.port=8080`
- 前端端口：`frontend/vite.config.ts` 中 `server.port=5173`
