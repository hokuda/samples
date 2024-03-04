package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // 何らかの条件で後続のフィルタやサーブレットに処理を渡さずに直接レスポンスを返す
        if (shouldSkipProcessing()) {
            ((javax.servlet.http.HttpServletResponse)response).sendRedirect("/hoge");
            //response.getWriter().write("Filtered response without calling next filter or servlet.\n");
            return; // チェーンを進めずに終了
        }

        // 後続のフィルタやサーブレットに処理を渡す
        chain.doFilter(request, response);
    }

    // 他のメソッドや設定が続く可能性があります

    private boolean shouldSkipProcessing() {
        // 処理をスキップする条件を記述
        return true; // 例として常にスキップするとしています
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 初期化処理が必要な場合はここで行う
    }

    @Override
    public void destroy() {
        // フィルタが破棄される際の後処理が必要な場合はここで行う
    }
}
