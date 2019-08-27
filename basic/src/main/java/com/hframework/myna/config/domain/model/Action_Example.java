package com.hframework.myna.config.domain.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Action_Example {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    protected Integer limitStart;

    protected Integer limitEnd;

    public Action_Example() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart=limitStart;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd=limitEnd;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCodeIsNull() {
            addCriterion("code is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("code is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("code =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("code <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("code >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("code >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("code <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("code <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("code like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("code not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("code in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("code not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("code between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("code not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("`name` is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("`name` is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("`name` =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("`name` <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("`name` >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("`name` >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("`name` <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("`name` <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("`name` like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("`name` not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("`name` in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("`name` not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("`name` between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("`name` not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNull() {
            addCriterion("description is null");
            return (Criteria) this;
        }

        public Criteria andDescriptionIsNotNull() {
            addCriterion("description is not null");
            return (Criteria) this;
        }

        public Criteria andDescriptionEqualTo(String value) {
            addCriterion("description =", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotEqualTo(String value) {
            addCriterion("description <>", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThan(String value) {
            addCriterion("description >", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("description >=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThan(String value) {
            addCriterion("description <", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLessThanOrEqualTo(String value) {
            addCriterion("description <=", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionLike(String value) {
            addCriterion("description like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotLike(String value) {
            addCriterion("description not like", value, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionIn(List<String> values) {
            addCriterion("description in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotIn(List<String> values) {
            addCriterion("description not in", values, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionBetween(String value1, String value2) {
            addCriterion("description between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andDescriptionNotBetween(String value1, String value2) {
            addCriterion("description not between", value1, value2, "description");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("`TYPE` is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("`TYPE` is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(Byte value) {
            addCriterion("`TYPE` =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(Byte value) {
            addCriterion("`TYPE` <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(Byte value) {
            addCriterion("`TYPE` >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("`TYPE` >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(Byte value) {
            addCriterion("`TYPE` <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(Byte value) {
            addCriterion("`TYPE` <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<Byte> values) {
            addCriterion("`TYPE` in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<Byte> values) {
            addCriterion("`TYPE` not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(Byte value1, Byte value2) {
            addCriterion("`TYPE` between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("`TYPE` not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andActionScopeIsNull() {
            addCriterion("action_scope is null");
            return (Criteria) this;
        }

        public Criteria andActionScopeIsNotNull() {
            addCriterion("action_scope is not null");
            return (Criteria) this;
        }

        public Criteria andActionScopeEqualTo(Byte value) {
            addCriterion("action_scope =", value, "actionScope");
            return (Criteria) this;
        }

        public Criteria andActionScopeNotEqualTo(Byte value) {
            addCriterion("action_scope <>", value, "actionScope");
            return (Criteria) this;
        }

        public Criteria andActionScopeGreaterThan(Byte value) {
            addCriterion("action_scope >", value, "actionScope");
            return (Criteria) this;
        }

        public Criteria andActionScopeGreaterThanOrEqualTo(Byte value) {
            addCriterion("action_scope >=", value, "actionScope");
            return (Criteria) this;
        }

        public Criteria andActionScopeLessThan(Byte value) {
            addCriterion("action_scope <", value, "actionScope");
            return (Criteria) this;
        }

        public Criteria andActionScopeLessThanOrEqualTo(Byte value) {
            addCriterion("action_scope <=", value, "actionScope");
            return (Criteria) this;
        }

        public Criteria andActionScopeIn(List<Byte> values) {
            addCriterion("action_scope in", values, "actionScope");
            return (Criteria) this;
        }

        public Criteria andActionScopeNotIn(List<Byte> values) {
            addCriterion("action_scope not in", values, "actionScope");
            return (Criteria) this;
        }

        public Criteria andActionScopeBetween(Byte value1, Byte value2) {
            addCriterion("action_scope between", value1, value2, "actionScope");
            return (Criteria) this;
        }

        public Criteria andActionScopeNotBetween(Byte value1, Byte value2) {
            addCriterion("action_scope not between", value1, value2, "actionScope");
            return (Criteria) this;
        }

        public Criteria andTriggerResultIsNull() {
            addCriterion("trigger_result is null");
            return (Criteria) this;
        }

        public Criteria andTriggerResultIsNotNull() {
            addCriterion("trigger_result is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerResultEqualTo(String value) {
            addCriterion("trigger_result =", value, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultNotEqualTo(String value) {
            addCriterion("trigger_result <>", value, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultGreaterThan(String value) {
            addCriterion("trigger_result >", value, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultGreaterThanOrEqualTo(String value) {
            addCriterion("trigger_result >=", value, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultLessThan(String value) {
            addCriterion("trigger_result <", value, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultLessThanOrEqualTo(String value) {
            addCriterion("trigger_result <=", value, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultLike(String value) {
            addCriterion("trigger_result like", value, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultNotLike(String value) {
            addCriterion("trigger_result not like", value, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultIn(List<String> values) {
            addCriterion("trigger_result in", values, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultNotIn(List<String> values) {
            addCriterion("trigger_result not in", values, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultBetween(String value1, String value2) {
            addCriterion("trigger_result between", value1, value2, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andTriggerResultNotBetween(String value1, String value2) {
            addCriterion("trigger_result not between", value1, value2, "triggerResult");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdIsNull() {
            addCriterion("default_utterance_id is null");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdIsNotNull() {
            addCriterion("default_utterance_id is not null");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdEqualTo(Long value) {
            addCriterion("default_utterance_id =", value, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdNotEqualTo(Long value) {
            addCriterion("default_utterance_id <>", value, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdGreaterThan(Long value) {
            addCriterion("default_utterance_id >", value, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdGreaterThanOrEqualTo(Long value) {
            addCriterion("default_utterance_id >=", value, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdLessThan(Long value) {
            addCriterion("default_utterance_id <", value, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdLessThanOrEqualTo(Long value) {
            addCriterion("default_utterance_id <=", value, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdIn(List<Long> values) {
            addCriterion("default_utterance_id in", values, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdNotIn(List<Long> values) {
            addCriterion("default_utterance_id not in", values, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdBetween(Long value1, Long value2) {
            addCriterion("default_utterance_id between", value1, value2, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andDefaultUtteranceIdNotBetween(Long value1, Long value2) {
            addCriterion("default_utterance_id not between", value1, value2, "defaultUtteranceId");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("`status` is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("`status` is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Byte value) {
            addCriterion("`status` =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Byte value) {
            addCriterion("`status` <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Byte value) {
            addCriterion("`status` >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Byte value) {
            addCriterion("`status` >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Byte value) {
            addCriterion("`status` <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Byte value) {
            addCriterion("`status` <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Byte> values) {
            addCriterion("`status` in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Byte> values) {
            addCriterion("`status` not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Byte value1, Byte value2) {
            addCriterion("`status` between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Byte value1, Byte value2) {
            addCriterion("`status` not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNull() {
            addCriterion("creator_id is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIsNotNull() {
            addCriterion("creator_id is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorIdEqualTo(Long value) {
            addCriterion("creator_id =", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotEqualTo(Long value) {
            addCriterion("creator_id <>", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThan(Long value) {
            addCriterion("creator_id >", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdGreaterThanOrEqualTo(Long value) {
            addCriterion("creator_id >=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThan(Long value) {
            addCriterion("creator_id <", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdLessThanOrEqualTo(Long value) {
            addCriterion("creator_id <=", value, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdIn(List<Long> values) {
            addCriterion("creator_id in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotIn(List<Long> values) {
            addCriterion("creator_id not in", values, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdBetween(Long value1, Long value2) {
            addCriterion("creator_id between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreatorIdNotBetween(Long value1, Long value2) {
            addCriterion("creator_id not between", value1, value2, "creatorId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andModifierIdIsNull() {
            addCriterion("modifier_id is null");
            return (Criteria) this;
        }

        public Criteria andModifierIdIsNotNull() {
            addCriterion("modifier_id is not null");
            return (Criteria) this;
        }

        public Criteria andModifierIdEqualTo(Long value) {
            addCriterion("modifier_id =", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotEqualTo(Long value) {
            addCriterion("modifier_id <>", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdGreaterThan(Long value) {
            addCriterion("modifier_id >", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdGreaterThanOrEqualTo(Long value) {
            addCriterion("modifier_id >=", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdLessThan(Long value) {
            addCriterion("modifier_id <", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdLessThanOrEqualTo(Long value) {
            addCriterion("modifier_id <=", value, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdIn(List<Long> values) {
            addCriterion("modifier_id in", values, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotIn(List<Long> values) {
            addCriterion("modifier_id not in", values, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdBetween(Long value1, Long value2) {
            addCriterion("modifier_id between", value1, value2, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifierIdNotBetween(Long value1, Long value2) {
            addCriterion("modifier_id not between", value1, value2, "modifierId");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNull() {
            addCriterion("modify_time is null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIsNotNull() {
            addCriterion("modify_time is not null");
            return (Criteria) this;
        }

        public Criteria andModifyTimeEqualTo(Date value) {
            addCriterion("modify_time =", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotEqualTo(Date value) {
            addCriterion("modify_time <>", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThan(Date value) {
            addCriterion("modify_time >", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("modify_time >=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThan(Date value) {
            addCriterion("modify_time <", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeLessThanOrEqualTo(Date value) {
            addCriterion("modify_time <=", value, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeIn(List<Date> values) {
            addCriterion("modify_time in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotIn(List<Date> values) {
            addCriterion("modify_time not in", values, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeBetween(Date value1, Date value2) {
            addCriterion("modify_time between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andModifyTimeNotBetween(Date value1, Date value2) {
            addCriterion("modify_time not between", value1, value2, "modifyTime");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseIsNull() {
            addCriterion("trigger_case is null");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseIsNotNull() {
            addCriterion("trigger_case is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseEqualTo(Byte value) {
            addCriterion("trigger_case =", value, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseNotEqualTo(Byte value) {
            addCriterion("trigger_case <>", value, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseGreaterThan(Byte value) {
            addCriterion("trigger_case >", value, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseGreaterThanOrEqualTo(Byte value) {
            addCriterion("trigger_case >=", value, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseLessThan(Byte value) {
            addCriterion("trigger_case <", value, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseLessThanOrEqualTo(Byte value) {
            addCriterion("trigger_case <=", value, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseIn(List<Byte> values) {
            addCriterion("trigger_case in", values, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseNotIn(List<Byte> values) {
            addCriterion("trigger_case not in", values, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseBetween(Byte value1, Byte value2) {
            addCriterion("trigger_case between", value1, value2, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerCaseNotBetween(Byte value1, Byte value2) {
            addCriterion("trigger_case not between", value1, value2, "triggerCase");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueIsNull() {
            addCriterion("trigger_default_value is null");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueIsNotNull() {
            addCriterion("trigger_default_value is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueEqualTo(String value) {
            addCriterion("trigger_default_value =", value, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueNotEqualTo(String value) {
            addCriterion("trigger_default_value <>", value, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueGreaterThan(String value) {
            addCriterion("trigger_default_value >", value, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueGreaterThanOrEqualTo(String value) {
            addCriterion("trigger_default_value >=", value, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueLessThan(String value) {
            addCriterion("trigger_default_value <", value, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueLessThanOrEqualTo(String value) {
            addCriterion("trigger_default_value <=", value, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueLike(String value) {
            addCriterion("trigger_default_value like", value, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueNotLike(String value) {
            addCriterion("trigger_default_value not like", value, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueIn(List<String> values) {
            addCriterion("trigger_default_value in", values, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueNotIn(List<String> values) {
            addCriterion("trigger_default_value not in", values, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueBetween(String value1, String value2) {
            addCriterion("trigger_default_value between", value1, value2, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerDefaultValueNotBetween(String value1, String value2) {
            addCriterion("trigger_default_value not between", value1, value2, "triggerDefaultValue");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeIsNull() {
            addCriterion("trigger_value_type is null");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeIsNotNull() {
            addCriterion("trigger_value_type is not null");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeEqualTo(Byte value) {
            addCriterion("trigger_value_type =", value, "triggerValueType");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeNotEqualTo(Byte value) {
            addCriterion("trigger_value_type <>", value, "triggerValueType");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeGreaterThan(Byte value) {
            addCriterion("trigger_value_type >", value, "triggerValueType");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeGreaterThanOrEqualTo(Byte value) {
            addCriterion("trigger_value_type >=", value, "triggerValueType");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeLessThan(Byte value) {
            addCriterion("trigger_value_type <", value, "triggerValueType");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeLessThanOrEqualTo(Byte value) {
            addCriterion("trigger_value_type <=", value, "triggerValueType");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeIn(List<Byte> values) {
            addCriterion("trigger_value_type in", values, "triggerValueType");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeNotIn(List<Byte> values) {
            addCriterion("trigger_value_type not in", values, "triggerValueType");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeBetween(Byte value1, Byte value2) {
            addCriterion("trigger_value_type between", value1, value2, "triggerValueType");
            return (Criteria) this;
        }

        public Criteria andTriggerValueTypeNotBetween(Byte value1, Byte value2) {
            addCriterion("trigger_value_type not between", value1, value2, "triggerValueType");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}