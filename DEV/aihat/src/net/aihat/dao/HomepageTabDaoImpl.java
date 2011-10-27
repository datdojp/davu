package net.aihat.dao;

import java.util.List;
import net.aihat.dto.HomepageTabDto;
import org.springframework.dao.DataAccessException;

public class HomepageTabDaoImpl extends BaseDao implements HomepageTabDao {
	public List<HomepageTabDto> getAll() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("selectAllHomepageTabs");
	}

	public void delete(int id) throws DataAccessException {
		getSqlMapClientTemplate().delete("deleteHomepageTab", id);
	}

}
