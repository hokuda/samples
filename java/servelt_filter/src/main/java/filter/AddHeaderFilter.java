package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

//@WebFilter("/*") // すべてのリクエストに対してフィルタを適用
public class AddHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        
        // フィルタ内でServletResponseをHttpServletResponseにキャスト
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // カスタムのヘッダを追加
        httpResponse.addHeader("X-Custom-Header", "=== Custom Header Value ===");

        // チェーンを進める
        chain.doFilter(request, httpResponse);
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
