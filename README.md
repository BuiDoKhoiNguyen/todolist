## API Documentation

### Authentication APIs

| Method | Endpoint                       | Description                      | Request Body                                    |
| ------ | ------------------------------ | -------------------------------- | ----------------------------------------------- |
| POST   | `/api/authenticate`            | Đăng nhập hệ thống               | `{username, password, rememberMe}`              |
| GET    | `/api/authenticate`            | Kiểm tra trạng thái đăng nhập    | -                                               |
| POST   | `/api/register`                | Đăng ký tài khoản mới            | `{login, email, password, firstName, lastName}` |
| GET    | `/api/activate`                | Kích hoạt tài khoản              | `?key=activation_key`                           |
| GET    | `/api/account`                 | Lấy thông tin tài khoản hiện tại | -                                               |
| POST   | `/api/account`                 | Cập nhật thông tin tài khoản     | `{firstName, lastName, email, langKey}`         |
| POST   | `/api/account/change-password` | Đổi mật khẩu                     | `{currentPassword, newPassword}`                |

### Task Management APIs

| Method | Endpoint                         | Description                 | Authorization |
| ------ | -------------------------------- | --------------------------- | ------------- |
| POST   | `/api/tasks`                     | Tạo task mới                | User/Admin    |
| GET    | `/api/tasks`                     | Lấy danh sách tất cả tasks  | User/Admin    |
| GET    | `/api/tasks/{id}`                | Lấy chi tiết task theo ID   | User/Admin    |
| PUT    | `/api/tasks/{id}`                | Cập nhật task               | User/Admin    |
| PATCH  | `/api/tasks/{id}`                | Cập nhật một phần task      | User/Admin    |
| DELETE | `/api/tasks/{id}`                | Xóa task                    | User/Admin    |
| GET    | `/api/tasks/status/{status}`     | Lấy tasks theo trạng thái   | User/Admin    |
| GET    | `/api/tasks/priority/{priority}` | Lấy tasks theo độ ưu tiên   | User/Admin    |
| GET    | `/api/tasks/overdue`             | Lấy tasks quá hạn           | User/Admin    |
| GET    | `/api/tasks/search`              | Tìm kiếm tasks theo title   | User/Admin    |
| GET    | `/api/tasks/my-tasks`            | Lấy tasks của user hiện tại | User/Admin    |

### Category Management APIs

| Method | Endpoint               | Description              | Authorization |
| ------ | ---------------------- | ------------------------ | ------------- |
| POST   | `/api/categories`      | Tạo category mới         | User/Admin    |
| GET    | `/api/categories`      | Lấy danh sách categories | User/Admin    |
| GET    | `/api/categories/{id}` | Lấy chi tiết category    | User/Admin    |
| PUT    | `/api/categories/{id}` | Cập nhật category        | User/Admin    |
| DELETE | `/api/categories/{id}` | Xóa category             | User/Admin    |

### Tag Management APIs

| Method | Endpoint                | Description        | Authorization |
| ------ | ----------------------- | ------------------ | ------------- |
| POST   | `/api/tags`             | Tạo tag mới        | User/Admin    |
| GET    | `/api/tags`             | Lấy danh sách tags | User/Admin    |
| GET    | `/api/tags/{id}`        | Lấy chi tiết tag   | User/Admin    |
| GET    | `/api/tags/name/{name}` | Tìm tag theo tên   | User/Admin    |
| PUT    | `/api/tags/{id}`        | Cập nhật tag       | User/Admin    |
| DELETE | `/api/tags/{id}`        | Xóa tag            | User/Admin    |

### Subtask Management APIs

| Method | Endpoint                      | Description               | Authorization |
| ------ | ----------------------------- | ------------------------- | ------------- |
| POST   | `/api/subtasks`               | Tạo subtask mới           | User/Admin    |
| GET    | `/api/subtasks/{id}`          | Lấy chi tiết subtask      | User/Admin    |
| PUT    | `/api/subtasks/{id}`          | Cập nhật subtask          | User/Admin    |
| DELETE | `/api/subtasks/{id}`          | Xóa subtask               | User/Admin    |
| GET    | `/api/subtasks/task/{taskId}` | Lấy subtasks theo task ID | User/Admin    |

### Comment Management APIs

| Method | Endpoint                      | Description               | Authorization |
| ------ | ----------------------------- | ------------------------- | ------------- |
| POST   | `/api/comments`               | Tạo comment mới           | User/Admin    |
| GET    | `/api/comments/{id}`          | Lấy chi tiết comment      | User/Admin    |
| PUT    | `/api/comments/{id}`          | Cập nhật comment          | User/Admin    |
| DELETE | `/api/comments/{id}`          | Xóa comment               | User/Admin    |
| GET    | `/api/comments/task/{taskId}` | Lấy comments theo task ID | User/Admin    |

### Roles & Permissions

- **ROLE_ADMIN**: Quản lý users, có quyền truy cập tất cả endpoints
- **ROLE_USER**: Quản lý tasks, categories, tags, comments của mình

## System Architecture & Workflows

### Database Design

![Database Diagram](screenshots/database_diagrame.png)

### Authentication Flow

![Authentication Flow](screenshots/authentication_flow.png)

**Workflow bao gồm:**

- User Registration với email verification
- JWT-based Authentication
- Role-based Authorization
- Password reset functionality

### Task Management Flow

![Task Management Flow](screenshots/task_management_flow.png)

**Các chức năng chính:**

- CRUD operations cho Tasks
- Search và filter theo status, priority
- Pagination support
- User-specific task management

### Subtask Management Flow

![Subtask Management Flow](screenshots/subtask_management_flow.png)

**Tính năng:**

- Tạo subtasks liên kết với parent task
- Quản lý status cho từng subtask
- Cascade operations với parent task

### Comment Management Flow

![Comment Management Flow](screenshots/comment_management_flow.png)

**Chức năng:**

- Thêm comments cho tasks
- User authentication cho comments
- Thread discussions trên tasks

## Business Logic

### Task Lifecycle

1. **Tạo Task**: User tạo task mới với title, description, priority, due date
2. **Phân loại**: Gán category và tags cho task
3. **Chia nhỏ**: Tạo subtasks để chia nhỏ công việc
4. **Theo dõi**: Cập nhật status (PENDING → IN_PROGRESS → COMPLETED)
5. **Thảo luận**: Thêm comments để trao đổi
6. **Hoàn thành**: Đánh dấu task hoàn thành hoặc hủy bỏ

### Priority Management

- **URGENT**: Cần xử lý ngay lập tức
- **HIGH**: Ưu tiên cao
- **MEDIUM**: Ưu tiên trung bình (default)
- **LOW**: Ưu tiên thấp

### Status Tracking

- **PENDING**: Chờ xử lý (default)
- **IN_PROGRESS**: Đang thực hiện
- **COMPLETED**: Đã hoàn thành
- **CANCELLED**: Đã hủy bỏ
