package com.wanted.market.product.repository;

import com.wanted.market.product.domain.Product;
import com.wanted.market.product.domain.ProductStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ProductRepositoryImpl implements ProductRepository {
    private long autoIncrementId = 1L;
    private final Map<Long, Product> store = new HashMap<>();

    @Override
    public Product save(Product product) {
        store.put(autoIncrementId++, product);
        return product;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        return Optional.ofNullable(store.get(productId));
    }

    @Override
    public List<Product> findAll() {
        return store.entrySet().stream().map(entry -> store.get(entry.getKey())).toList();
    }

    @Override
    public List<Product> findPurchasedProductsByMemberId() {
        // To-Do : 일치하는 유저 ID를 먼저 조회하라.
        return store.entrySet().stream().map(entry -> store.get(entry.getKey()))
                .filter(product -> product.getStatus().equals(ProductStatus.SOLD))
                .toList();
    }

    @Override
    public List<Product> findReservedProductsByMemberId() {
        // To-Do : Product로부터 상태를 가져올 방법 구상하라.
        return null;
    }

    @Override
    public void deleteById(Long productId) {
        store.remove(productId);
    }
}

/*
public class FakeIssueRepository implements IssueRepository {
    private long autoIncrementId = 1L;
    private final Map<Long, Issue> store = new HashMap<>();
    private final ProjectRepository projectRepository;
    private final MemberRepository memberRepository;

    public FakeIssueRepository(ProjectRepository projectRepository, MemberRepository memberRepository) {
        this.projectRepository = projectRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public Issue save(Issue issue) {
        store.put(autoIncrementId++, issue);
        return issue;
    }

    @Override
    public Optional<Issue> findById(Long issueId) {
        return Optional.ofNullable(store.get(issueId));
    }

    @Override
    public List<Issue> findByProjectId(Long projectId) {
        return store.entrySet().stream()
                .filter(entry -> entry.getValue().getProject()
                        .equals(projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new)))
                .map(entry -> store.get(entry.getKey()))
                .toList();
    }

    @Override
    public void deleteById(Long issueId) {
        store.remove(issueId);
    }

    @Override
    public List<Issue> findByManager(Long projectId, Long managerId) {
        return store.values().stream()
                .filter(issue -> issue.getProject().equals(projectRepository.findById(projectId).orElseThrow(ProjectNotFoundException::new)))
                .filter(issue -> issue.getManager().equals(memberRepository.findById(managerId).orElseThrow(EntityNotFoundException::new)))
                .toList();
    }

    @Override
    public List<Issue> findLikeTopic(Long projectId, String topic) {
        return store.values().stream()
                .filter(issue -> issue.getTopic().contains(topic))
                .toList();
    }

    @Override
    public List<Issue> findOrderByPriorityAsc(Long projectId) {
        return store.values().stream()
                .sorted(Comparator.comparingInt(issue -> issue.getPriority().getValue()))
                .toList();
    }

    @Override
    public List<Issue> findOrderByPriorityDesc(Long projectId) {
        return  store.values().stream()
                .sorted(Comparator.comparingInt(issue -> -issue.getPriority().getValue()))
                .toList();
    }

    @Override
    public List<Issue> findByProgress(Long projectId, IssueProgress progress) {
        return  store.values().stream()
                .filter(issue -> issue.getProgress().equals(progress))
                .toList();
    }

    @Override
    public List<Issue> findByCategory(Long projectId, IssueCategory category) {
        return store.values().stream()
                .filter(issue -> issue.getCategory().equals(category))
                .toList();
    }
}*/