package kotlins

//深度遍历
fun numIslands(grid: Array<CharArray>): Int {
    var count = 0
    for (i in 0 until grid.size) {
        for (j in 0 until grid[0].size) {
            if (grid[i][j] == '1') count++
            dfs(grid, i, j)
        }
    }
    return count
}

fun dfs(grid: Array<CharArray>, i: Int, j: Int) {
    if (i >= 0 && i < grid.size && j >= 0 && j < grid[0].size && grid[i][j] == '1') {
        grid[i][j] = '0'
        dfs(grid, i + 1, j)
        dfs(grid, i - 1, j)
        dfs(grid, i, j + 1)
        dfs(grid, i, j - 1)
    }
}

//广度遍历

//并查集
