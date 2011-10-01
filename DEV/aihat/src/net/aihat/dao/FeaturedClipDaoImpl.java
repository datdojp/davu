package net.aihat.dao;

import java.util.List;

import net.aihat.dto.FeaturedClipDto;

import org.springframework.dao.DataAccessException;

public class FeaturedClipDaoImpl extends BaseDao implements FeaturedClipDao {
	public void insert(FeaturedClipDto featuredClip) throws DataAccessException {
		getSqlMapClientTemplate().insert("insertFeaturedClip", featuredClip);
	}

	public List<FeaturedClipDto> search() throws DataAccessException {
		return getSqlMapClientTemplate().queryForList("searchFeaturedClip");
	}

	public void update(FeaturedClipDto featuredClip) throws DataAccessException {
		getSqlMapClientTemplate().update("updateFeaturedClip", featuredClip);
	}

	public void delete(FeaturedClipDto featuredClip) throws DataAccessException {
		getSqlMapClientTemplate().delete("deleteFeaturedClip", featuredClip);
	}
}
