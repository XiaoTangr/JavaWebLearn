package cn.javat.javaLearn.experiment4.dao.Impl;

import cn.javat.javaLearn.experiment4.dao.PromotionStatisticsDao;
import cn.javat.javaLearn.experiment4.entity.PromotionStatisticsEntity;
import cn.javat.javaLearn.experiment4.utils.DBUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动统计DAO实现类
 */
public class PromotionStatisticsDaoImpl implements PromotionStatisticsDao {
    
    @Override
    public boolean createStatistics(PromotionStatisticsEntity statistics) {
        String sql = "INSERT INTO promotion_statistics (promotion_id, coupon_id, participation_count, order_count, total_discount, total_sales, statistics_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, statistics.getPromotionId());
            if (statistics.getCouponId() != null) {
                pstmt.setLong(2, statistics.getCouponId());
            } else {
                pstmt.setNull(2, Types.BIGINT);
            }
            pstmt.setInt(3, statistics.getParticipationCount());
            pstmt.setInt(4, statistics.getOrderCount());
            pstmt.setBigDecimal(5, statistics.getTotalDiscount());
            pstmt.setBigDecimal(6, statistics.getTotalSales());
            pstmt.setDate(7, new java.sql.Date(statistics.getStatisticsDate().getTime()));
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public PromotionStatisticsEntity getStatisticsById(Long id) {
        String sql = "SELECT * FROM promotion_statistics WHERE id = ?";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToStatistics(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public List<PromotionStatisticsEntity> getStatisticsByPromotionId(Long promotionId) {
        String sql = "SELECT * FROM promotion_statistics WHERE promotion_id = ?";
        List<PromotionStatisticsEntity> statisticsList = new ArrayList<>();
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, promotionId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                statisticsList.add(mapResultSetToStatistics(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return statisticsList;
    }
    
    @Override
    public boolean updateStatistics(PromotionStatisticsEntity statistics) {
        String sql = "UPDATE promotion_statistics SET promotion_id = ?, coupon_id = ?, participation_count = ?, order_count = ?, total_discount = ?, total_sales = ?, statistics_date = ? WHERE id = ?";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, statistics.getPromotionId());
            if (statistics.getCouponId() != null) {
                pstmt.setLong(2, statistics.getCouponId());
            } else {
                pstmt.setNull(2, Types.BIGINT);
            }
            pstmt.setInt(3, statistics.getParticipationCount());
            pstmt.setInt(4, statistics.getOrderCount());
            pstmt.setBigDecimal(5, statistics.getTotalDiscount());
            pstmt.setBigDecimal(6, statistics.getTotalSales());
            pstmt.setDate(7, new java.sql.Date(statistics.getStatisticsDate().getTime()));
            pstmt.setLong(8, statistics.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteStatistics(Long id) {
        String sql = "DELETE FROM promotion_statistics WHERE id = ?";
        
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
    public List<PromotionStatisticsEntity> getAllStatistics() {
        String sql = "SELECT * FROM promotion_statistics";
        List<PromotionStatisticsEntity> statisticsList = new ArrayList<>();
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                statisticsList.add(mapResultSetToStatistics(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return statisticsList;
    }
    
    private PromotionStatisticsEntity mapResultSetToStatistics(ResultSet rs) throws SQLException {
        PromotionStatisticsEntity statistics = new PromotionStatisticsEntity();
        statistics.setId(rs.getLong("id"));
        statistics.setPromotionId(rs.getLong("promotion_id"));
        if (rs.getObject("coupon_id") != null) {
            statistics.setCouponId(rs.getLong("coupon_id"));
        }
        statistics.setParticipationCount(rs.getInt("participation_count"));
        statistics.setOrderCount(rs.getInt("order_count"));
        statistics.setTotalDiscount(rs.getBigDecimal("total_discount"));
        statistics.setTotalSales(rs.getBigDecimal("total_sales"));
        statistics.setStatisticsDate(rs.getDate("statistics_date"));
        return statistics;
    }
}