import cn.org.atool.generator.FileGenerator;
import cn.org.atool.generator.annotation.Table;
import cn.org.atool.generator.annotation.Tables;
import org.junit.jupiter.api.Test;

public class EntityGeneratorDemo {
	// 数据源 url
	static final String url = "jdbc:mysql://localhost:3306/cleanarch?useUnicode=true&characterEncoding=utf8";
	// 数据库用户名
	static final String username = "root";
	// 数据库密码
	static final String password = "P@88w0rd";

	@Test
	public void demo() {
		FileGenerator.build(Empty.class);
	}

	@Tables(
			// 设置数据库连接信息
			url = url, username = username, password = password,
			// 设置entity类生成src目录, 相对于 user.dir
			srcDir = "src/main/java",
			// 设置entity类的package值
			basePack = "com.github.jgzl.application",
			// 设置dao接口和实现的src目录, 相对于 user.dir
			daoDir = "src/main/java",
			// 设置哪些表要生成Entity文件
			tables = {@Table(value = {"t_order"})}
	)
	static class Empty { //类名随便取, 只是配置定义的一个载体
	}
}