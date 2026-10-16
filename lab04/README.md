# Lab04：Git 练习记录

课程说明：https://sp25.datastructur.es/labs/lab04/

## 完成状态

已完成 Part 1.1 和 Part 2.1–2.3 的本地操作，并核对文件与提交历史。
你目前是自学、无法登录 Beacon，因此尚未完成两份课程表单，也未取得第一行口令。
`magic_word.txt` 第一行有意留空，**目前不满足完整提交要求**。

| 文件行号 | 对应部分 | 内容 |
| --- | --- | --- |
| 1 | Part 1 的两份表单 | 留空，等待 Beacon 提供的 magic word |
| 2 | Part 2.1 | `matcha` |
| 3 | Part 2.2 | `diet coke`（中间有一个空格） |
| 4 | Part 2.3 | `boba` |

以后取得表单口令时，替换第一行空行，不要再插入一行。
课程要求只向 Gradescope 提交 `magic_word.txt`；本记录和两个练习仓库无需提交。

## Part 1.1：提交与恢复

练习仓库：`C:\Users\Henti\Desktop\Learning\CS61B\lab04-checkoff`

开始时该目录已初始化 Git，两个文本文件为空，尚无提交。完成后的历史为：

| 提交 | 提交说明 | 保存的内容 |
| --- | --- | --- |
| `fb607ed` | `Add 61b.txt` | 只包含 `61b.txt`，内容为 `Created 61b.txt` |
| `3887e75` | `Updated 61b.txt and added 61boba.txt` | `61b.txt` 为 `61b.txt changed to version 2`；新增 `61boba.txt`，内容为 `Created 61boba.txt` |

随后把 `61b.txt` 改为 `61b.txt changed to final version`，不提交，再依次执行：

```powershell
git restore -- 61b.txt
git restore --source=fb607ed -- 61b.txt
```

第一次恢复后已核对内容为 version 2；第二次恢复后为 `Created 61b.txt`。
最终 `git status --short` 显示 ` M 61b.txt`，这是题目最后一步的预期状态：
工作区文件恢复到第一次提交的内容，但 HEAD 和暂存区仍指向第二次提交的版本。
`restore` 恢复文件，不会移动分支或删除提交。

## Part 2：历史、分支和远程合并

练习仓库：`C:\Users\Henti\Desktop\Learning\CS61B\git-exercise-sp25`

该目录与主作业仓库、checkoff 仓库平级。按照官方脚本支持的 HTTPS 方式克隆了
`https://github.com/Berkeley-CS61B/git-exercise-sp25.git`。
以下命令均在这个独立练习仓库中执行。

1. 查阅 `git log`，找到 `8adf096`（`Add password`）。执行
   `git restore --source=8adf096 -- password.txt` 后读到 `matcha`，
   再执行 `git restore -- password.txt` 恢复当前提交的文件。
2. 执行 `git switch erik`，在 `password.txt` 中读到 `diet coke`。
   当前提交为 `ade7854`，提交说明为 `task 2 password`。随后执行 `git switch main`。
3. 添加官方练习远程仓库并合并：

   ```powershell
   git remote add remote-exercise https://github.com/Berkeley-CS61B/git-exercise-remote.git
   git pull remote-exercise main --allow-unrelated-histories --no-rebase --no-edit
   ```

   合并产生 `final_password.txt`，其中的口令是 `boba`。
   合并提交为 `54be3e3`，有两个父提交；未发生冲突。

最终分支为 `main`，工作区干净。这里的 `--allow-unrelated-histories` 用于题目指定的
两个无共同历史的练习仓库，不应直接照搬到其他作业仓库。

## 验证结果

- checkoff 仓库正好有两次提交；第一次只跟踪 `61b.txt`，第二次跟踪两个文件。
- 两次恢复的内容均已核对；最终保留题目要求的未提交恢复结果。
- Part 2 的口令分别由历史版本、指定分支、合并后的文件读取，并非猜测。
- `magic_word.txt` 保留四行位置，使用 UTF-8 无 BOM，第 2–4 行无行尾空格。
- 本实验是 Git 操作练习，没有 Java 代码或单元测试需要运行。
