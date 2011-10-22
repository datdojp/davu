package net.aihat.dao;

import java.util.List;
import net.aihat.dto.HomePageTabDto;
import org.springframework.dao.DataAccessException;

public class HomepageTabDaoImpl extends BaseDao implements HomepageTabDao {
	public List<HomePageTabDto> getAllHomepageTab() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("selectAllHomepageTabs");
	}

}
