package cn.javat.javaLearn.experiment4.dao.Impl;

import cn.javat.javaLearn.experiment4.dao.PromotionDao;
import cn.javat.javaLearn.experiment4.entity.PromotionEntity;
import cn.javat.javaLearn.experiment4.utils.DBUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 促销活动DAO实现类
 */
public class PromotionDaoImpl implements PromotionDao {
    
    @Override
    public boolean createPromotion(PromotionEntity promotion) {
        String sql = "INSERT INTO promotions (name, description, type, discount, min_purchase, start_time, end_time, is_active) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, promotion.getName());
            pstmt.setString(2, promotion.getDescription());
            pstmt.setInt(3, promotion.getType());
            pstmt.setBigDecimal(4, promotion.getDiscount());
            pstmt.setBigDecimal(5, promotion.getMinPurchase());
            pstmt.setTimestamp(6, new Timestamp(promotion.getStartTime().getTime()));
            pstmt.setTimestamp(7, new Timestamp(promotion.getEndTime().getTime()));
            pstmt.setBoolean(8, promotion.getIsActive());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public PromotionEntity getPromotionById(Long id) {
        String sql = "SELECT * FROM promotions WHERE id = ?";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToPromotion(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public boolean updatePromotion(PromotionEntity promotion) {
        String sql = "UPDATE promotions SET name = ?, description = ?, type = ?, discount = ?, min_purchase = ?, start_time = ?, end_time = ?, is_active = ? WHERE id = ?";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, promotion.getName());
            pstmt.setString(2, promotion.getDescription());
            pstmt.setInt(3, promotion.getType());
            pstmt.setBigDecimal(4, promotion.getDiscount());
            pstmt.setBigDecimal(5, promotion.getMinPurchase());
            pstmt.setTimestamp(6, new Timestamp(promotion.getStartTime().getTime()));
            pstmt.setTimestamp(7, new Timestamp(promotion.getEndTime().getTime()));
            pstmt.setBoolean(8, promotion.getIsActive());
            pstmt.setLong(9, promotion.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deletePromotion(Long id) {
        String sql = "DELETE FROM promotions WHERE id = ?";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public List<PromotionEntity> getAllValidPromotions() {
        String sql = "SELECT * FROM promotions WHERE is_active = true AND end_time > NOW()";
        return getPromotionsBySql(sql);
    }
    
    @Override
    public List<PromotionEntity> getAllPromotions() {
        String sql = "SELECT * FROM promotions";
        return getPromotionsBySql(sql);
    }
    
    private List<PromotionEntity> getPromotionsBySql(String sql) {
        List<PromotionEntity> promotions = new ArrayList<>();
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                promotions.add(mapResultSetToPromotion(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return promotions;
    }
    
    private PromotionEntity mapResultSetToPromotion(ResultSet rs) throws SQLException {
        PromotionEntity promotion = new PromotionEntity();
        promotion.setId(rs.getLong("id"));
        promotion.setName(rs.getString("name"));
        promotion.setDescription(rs.getString("description"));
        promotion.setType(rs.getInt("type"));
        promotion.setDiscount(rs.getBigDecimal("discount"));
        promotion.setMinPurchase(rs.getBigDecimal("min_purchase"));
        promotion.setStartTime(rs.getTimestamp("start_time"));
        promotion.setEndTime(rs.getTimestamp("end_time"));
        promotion.setIsActive(rs.getBoolean("is_active"));
        return promotion;
    }
}