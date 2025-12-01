package cn.javat.javaLearn.experiment4.dao.Impl;

import cn.javat.javaLearn.experiment4.dao.CouponDao;
import cn.javat.javaLearn.experiment4.entity.CouponEntity;
import cn.javat.javaLearn.experiment4.utils.DBUtils;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 优惠券DAO实现类
 */
public class CouponDaoImpl implements CouponDao {
    
    @Override
    public boolean createCoupon(CouponEntity coupon) {
        String sql = "INSERT INTO coupons (code, name, discount, min_amount, start_time, end_time, is_active, total_count, used_count) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, coupon.getCode());
            pstmt.setString(2, coupon.getName());
            pstmt.setBigDecimal(3, coupon.getDiscount());
            pstmt.setBigDecimal(4, coupon.getMinAmount());
            pstmt.setTimestamp(5, new Timestamp(coupon.getStartTime().getTime()));
            pstmt.setTimestamp(6, new Timestamp(coupon.getEndTime().getTime()));
            pstmt.setBoolean(7, coupon.getIsActive());
            pstmt.setInt(8, coupon.getTotalCount());
            pstmt.setInt(9, coupon.getUsedCount());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public CouponEntity getCouponById(Long id) {
        String sql = "SELECT * FROM coupons WHERE id = ?";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCoupon(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public CouponEntity getCouponByCode(String code) {
        String sql = "SELECT * FROM coupons WHERE code = ?";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, code);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCoupon(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    @Override
    public boolean updateCoupon(CouponEntity coupon) {
        String sql = "UPDATE coupons SET code = ?, name = ?, discount = ?, min_amount = ?, start_time = ?, end_time = ?, is_active = ?, total_count = ?, used_count = ? WHERE id = ?";
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, coupon.getCode());
            pstmt.setString(2, coupon.getName());
            pstmt.setBigDecimal(3, coupon.getDiscount());
            pstmt.setBigDecimal(4, coupon.getMinAmount());
            pstmt.setTimestamp(5, new Timestamp(coupon.getStartTime().getTime()));
            pstmt.setTimestamp(6, new Timestamp(coupon.getEndTime().getTime()));
            pstmt.setBoolean(7, coupon.getIsActive());
            pstmt.setInt(8, coupon.getTotalCount());
            pstmt.setInt(9, coupon.getUsedCount());
            pstmt.setLong(10, coupon.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean deleteCoupon(Long id) {
        String sql = "DELETE FROM coupons WHERE id = ?";
        
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
    public List<CouponEntity> getAllValidCoupons() {
        String sql = "SELECT * FROM coupons WHERE is_active = true AND end_time > NOW()";
        return getCouponsBySql(sql);
    }
    
    @Override
    public List<CouponEntity> getAllCoupons() {
        String sql = "SELECT * FROM coupons";
        return getCouponsBySql(sql);
    }
    
    private List<CouponEntity> getCouponsBySql(String sql) {
        List<CouponEntity> coupons = new ArrayList<>();
        
        try (Connection conn = DBUtils.getInstance().getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                coupons.add(mapResultSetToCoupon(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return coupons;
    }
    
    private CouponEntity mapResultSetToCoupon(ResultSet rs) throws SQLException {
        CouponEntity coupon = new CouponEntity();
        coupon.setId(rs.getLong("id"));
        coupon.setCode(rs.getString("code"));
        coupon.setName(rs.getString("name"));
        coupon.setDiscount(rs.getBigDecimal("discount"));
        coupon.setMinAmount(rs.getBigDecimal("min_amount"));
        coupon.setStartTime(rs.getTimestamp("start_time"));
        coupon.setEndTime(rs.getTimestamp("end_time"));
        coupon.setIsActive(rs.getBoolean("is_active"));
        coupon.setTotalCount(rs.getInt("total_count"));
        coupon.setUsedCount(rs.getInt("used_count"));
        return coupon;
    }
}