package com.ayub.assessment.stream.event;

import java.math.BigDecimal;

public record ProductEvent(Long id,String name,String category,BigDecimal price) { }
