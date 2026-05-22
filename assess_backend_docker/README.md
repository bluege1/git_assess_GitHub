# 后端 Docker

1. 把 `backend/target/backend-1.0.0.jar` 复制到本目录，命名为 `backend-1.0.0.jar`
2. `cp .env.example .env` 并填写
3. `docker compose up -d --build`

健康检查：`curl http://localhost:9019/api/health`
